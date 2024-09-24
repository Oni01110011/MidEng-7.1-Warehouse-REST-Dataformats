# MidEng 7.1 Warehouse REST & Dataforma
von: Bernhard Aichinger-Ganas

am: 24.09.24

# Einführung

Es steht die Nationalratswahl vor der Tür und um die Wahlergebnisse möglichst rasch vom Wahllokal an die nächste Bezirksstelle übertragen zu können, soll eine REST Schnittstelle implementiert werden, wo die Ergebnisses (Anzahl der Stimmen pro Partei) dieses Wahllokals ausgegeben werden. Um die Möglichkeiten der Anbindung flexibel zu gestalten, sollen die Datenformate JSON und XML unterstützt werden.

# Grundkompetenz

## Daten-Struktur

- **Vorzugskandidaten.java**:
Diese Klasse repräsentiert einen Vorzugskandidaten einer Partei.
    - **Attribute**:
        - `partyID`: Eine Zeichenkette, die die ID der Partei angibt.
        - `listenNR`: Die Listenposition des Kandidaten.
        - `name`: Der Name des Kandidaten.
        - `stimmen`: Die Anzahl der erhaltenen Stimmen.
    - **Methoden**:
        - Getter und Setter für alle Attribute.
- **ElectionData.java**:
Diese Klasse sammelt Daten einer Wahlregion und enthält Informationen zu den Parteien und deren Ergebnisse.
    - **Attribute**:
        - `regionID`: ID der Region.
        - `regionName`: Name der Region.
        - `regionAddress`: Adresse der Region.
        - `regionPostalCode`: Postleitzahl der Region.
        - `federalState`: Bundesland.
        - `timestamp`: Zeitstempel der Datenerstellung.
        - `countingData`: Eine Liste von `Party`Objekten, die die Wahlergebnisse enthalten.
    - **Methoden**:
        - Konstruktoren für die Initialisierung von Objekten.
        - Getter und Setter für die Attribute, insbesondere für die Liste der Wahlergebnisse (`countingData`).
- **Party.java**:
Diese Klasse repräsentiert eine Partei in der Wahl.
    - **Attribute**:
        - `partyID`: ID der Partei.
        - `amountVotes`: Anzahl der erhaltenen Stimmen.
        - `vorzugskandidaten`: Eine Liste von `Vorzugskandidaten`, die Kandidaten der Partei repräsentieren.
    - **Methoden**:
        - Konstruktoren, Getter und Setter.
        - Eine Methode `sortiereVorzugskandidatenNachStimmen()`, die die Vorzugskandidaten basierend auf ihren Stimmen sortiert und die Listenposition (`listenNR`) anpasst.

Insgesamt ergibt sich eine hierarchische Struktur:

- **ElectionData** (enthält Region und Wahlinformationen)
    - **Party** (enthält Parteiinformationen und Vorzugskandidaten)
        - **Vorzugskandidaten** (enthält Kandidateninformationen)

## Daten-Generierung beim Wahllokal

Die Daten des Wahllokals werden in der [ElectionSimulator.java](http://ElectionSimulator.java) erstellt. 

```jsx
 public int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(501);
    }

    public ElectionData getData(int wID) {
        ElectionData data = new ElectionData();

        data.setRegionID(wID);
        data.setRegionName("TGM - Technologisches Gewerbe Museum");
        data.setRegionAddress("Wexstraße 19-23");
        data.setRegionPostalCode("Vienna");
        data.setFederalState("Austria");

        ArrayList<Vorzugskandidaten> vz = new ArrayList<>();
        vz.add(new Vorzugskandidaten("NJHGF", 212 ,"D1", generateRandomNumber()));
        vz.add(new Vorzugskandidaten("NJHGF", 212 ,"b", generateRandomNumber()));
        vz.add(new Vorzugskandidaten("NJHGF", 212 ,"D2", generateRandomNumber()));
```

Hierbei werden die Stimmen Random von 0 - 500 von der Methode `generateRandomNumber()` generiert. 

## Entwicklung einer REST Schnittstelle

Die  Klasse `ElectionController` ist eine Spring Boot REST-Controller-Klasse, die HTTP-Anfragen verarbeitet und auf bestimmte URL-Muster reagiert. 

### 1. **REST Controller**

```jsx
@RestController
public class ElectionController {
```

Die Klasse ist als `@RestController` annotiert. Dies bedeutet, dass sie RESTful Web Services bereitstellt. Alle Rückgabewerte von Methoden in einer solchen Klasse werden als HTTP-Response zurückgesendet. Spring kümmert sich automatisch um die Serialisierung der Rückgabewerte in das entsprechende Format (z. B. JSON oder XML).

### 2. **Dependency Injection mit `@Autowired`**

```jsx
@Autowired
private ElectionService whs = new ElectionService();
```

- `@Autowired` signalisiert, dass eine Instanz des `ElectionService` in den Controller injiziert wird. Dies ist eine der zentralen Konzepte in Spring, um die Abhängigkeiten zu verwalten.
- Der Controller verwendet `ElectionService` zur Beschaffung von Wahl-Daten, die über die HTTP-Endpunkte zurückgegeben werden.

### 3. **Request-Mapping**

- Die Klasse nutzt die Annotation `@RequestMapping`, um URLs auf Methoden zu mappen. Dies bedeutet, dass Anfragen, die bestimmte URLs betreffen, von den entsprechenden Methoden verarbeitet werden.

### a) **`@RequestMapping("/")`**

```jsx
@RequestMapping("/")
	public String warehouseMain() {
		String mainPage = "This is the election application! (DEZSYS_Election_REST) <br/><br/>" +
				"<a href='http://localhost:3389/election/001/json'>Link to Election/001/json</a><br/>" +
				"<a href='http://localhost:3389/election/001/xml'>Link to Election/001/xml</a><br/>" +
				"<a href='http://localhost:3389/001/consumer'>Link to Table/001/consumer/a><br/>";
		return mainPage;
	}
```

- Diese Methode wird aufgerufen, wenn jemand die Root-URL (`/`) der Anwendung aufruft. Sie gibt eine einfache HTML-Seite zurück, die Links zu weiteren Endpunkten der Anwendung enthält. Diese Methode hat keine explizite HTTP-Methodenbeschränkung, daher würde sie standardmäßig GET-Anfragen akzeptieren.
- Sie ist nützlich, um eine Übersicht oder eine Einstiegspunktseite bereitzustellen.

### b) **`@RequestMapping(value="/election/{eID}/json", produces = MediaType.APPLICATION_JSON_VALUE)`**

```jsx
@RequestMapping(value="/election/{eID}/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ElectionData electionDataJSON(@PathVariable int eID ) {
		return whs.getElectionData(eID);
	}
```

- Diese Methode verarbeitet GET-Anfragen, bei denen die URL das Muster `/election/{eID}/json` hat, wobei `{eID}` ein Platzhalter für eine Wahl-ID ist.
- Die Methode gibt die Wahl-Daten im JSON-Format zurück. Das wird durch die Verwendung von `produces = MediaType.APPLICATION_JSON_VALUE` festgelegt, das angibt, dass der Rückgabetyp als JSON serialisiert wird.

### 4. **`@PathVariable`**

```jsx
public ElectionData electionDataXML( @PathVariable int eID )
```

- In beiden Endpunkten (`/json` und `/xml`) wird `@PathVariable` verwendet, um den Wert von `eID` direkt aus der URL zu extrahieren. Dieser Wert wird dann an die Methode übergeben, um die entsprechenden Wahl-Daten für die jeweilige Wahl-ID zu beschaffen.

### 5. **HTTP und REST-Prinzipien**

- Der Controller folgt den Prinzipien von REST, indem er verschiedene Repräsentationen (JSON und XML) desselben Ressourcen-Datentyps (Wahl-Daten) bereitstellt, abhängig von der vom Client angeforderten URL.
- Der Client kann je nach Bedarf eine bestimmte Repräsentation anfordern, indem er die entsprechenden Endpunkte `/json` oder `/xml` verwendet.

### 6. **Medientypen (Content Types)**

```jsx
@RequestMapping(value="/election/{eID}/json", produces = MediaType.APPLICATION_JSON_VALUE)
```

- Die Verwendung von `MediaType.APPLICATION_JSON_VALUE` und `MediaType.APPLICATION_XML_VALUE` stellt sicher, dass die Antwort im richtigen Format zurückgesendet wird, was für REST-Dienste von zentraler Bedeutung ist, da verschiedene Clients möglicherweise unterschiedliche Datenformate verarbeiten möchten´

# Erweitertekompetenz

### 1. **Tabelle Rendern: `renderElectionTable`**

```jsx
function renderElectionTable(data) {
    const tableBody = document.querySelector('#electionTable tbody');
    const row = document.createElement('tr');
    row.innerHTML = `
        <td>${data.regionID}</td>
        <td>${data.regionName}</td>
        <td>${data.regionAddress}</td>
        <td>${data.regionPostalCode}</td>
        <td>${data.federalState}</td>
        <td>${data.timestamp}</td>
    `;
    tableBody.appendChild(row);
}
```

- **Was es macht**: Diese Funktion rendert die Haupttabelle mit den Wahldaten.
- **Wichtig**:
    - `document.querySelector('#electionTable tbody')`: Greift auf das `<tbody>` der Tabelle zu, wo die Daten eingefügt werden.
    - `document.createElement('tr')`: Erstellt eine neue Tabellenzeile (`<tr>`).
    - `row.innerHTML`: Füllt die Zellen der neuen Zeile mit den Daten aus dem `data`Objekt.
    - `tableBody.appendChild(row)`: Hängt die neue Zeile an das Tabellenkörper-Element an.

---

### 2. **Sortieren der Tabelle: `sortTableByVotes`**

```jsx
function sortTableByVotes(order) {
    const tableBody = document.querySelector('#partyTable tbody');
    const rows = Array.from(tableBody.querySelectorAll('tr'));

    rows.sort((a, b) => {
        const aVotes = parseInt(a.cells[1].textContent);
        const bVotes = parseInt(b.cells[1].textContent);
        return order === 'desc' ? bVotes - aVotes : aVotes - bVotes;
    });

    tableBody.innerHTML = '';
    rows.forEach(row => tableBody.appendChild(row)); 
}
```

- **Was es macht**: Diese Funktion sortiert die Tabelle mit den Parteidaten nach der Anzahl der Stimmen (Votes), entweder auf- oder absteigend.
- **Wichtig**:
    - `Array.from(tableBody.querySelectorAll('tr'))`: Holt alle Zeilen (`<tr>`) der Tabelle und wandelt sie in ein Array um, damit sie sortiert werden können.
    - `rows.sort()`: Sortiert die Zeilen basierend auf den Stimmen (`a.cells[1].textContent` greift auf die 2. Zelle zu, in der die Anzahl der Stimmen steht).
    - `tableBody.innerHTML = '';`: Leert den Inhalt des Tabellenkörpers, bevor die sortierten Zeilen neu eingefügt werden.

---

### 3. **Filterfunktion und Hervorhebung: `filterCandidates`**

```jsx
function filterCandidates() {
    const input = document.getElementById('candidateSearch').value.toLowerCase();
    const rows = document.querySelectorAll('#partyTable tbody tr');

    rows.forEach(row => {
        const candidatesCell = row.querySelector('.candidates-cell');
        const candidateNameSpans = candidatesCell.querySelectorAll('.candidate-name');

        let matchFound = false;
        candidateNameSpans.forEach(span => {
            const nameText = span.textContent.toLowerCase();
            if (nameText.includes(input)) {
                matchFound = true;
                const regExp = new RegExp(`(${input})`, 'gi');
                span.innerHTML = nameText.replace(regExp, '<span class="highlight">$1</span>');
            } else {
                span.innerHTML = span.textContent; 
            }
        });

        row.style.display = matchFound ? '' : 'none';
}
```

- **Was es macht**: Diese Funktion filtert die Tabelle basierend auf dem eingegebenen Namen der Vorzugskandidaten und hebt die Übereinstimmung hervor.
- **Wichtig**:
    - `document.getElementById('candidateSearch').value.toLowerCase()`: Holt den Wert des Suchfelds und wandelt ihn in Kleinbuchstaben um, um case-insensitive zu filtern.
    - `nameText.includes(input)`: Überprüft, ob der eingegebene Text im Namen des Kandidaten vorkommt.
    - `span.innerHTML = nameText.replace(regExp, '<span class="highlight">$1</span>')`: Hebt den gefundenen Text hervor, indem er in einen `<span>` mit der Klasse `highlight` eingeschlossen wird.
    - `row.style.display = matchFound ? '' : 'none'`: Versteckt die Zeilen, die keine Übereinstimmung haben.

## Probleme

### 1.  -parameter

### Aktivieren des `parameters` Flags beim Java-Compiler

Füge die Option `-parameters` in der `build.gradle`-Datei deines Projekts hinzu, um sicherzustellen, dass der Compiler die Parameterinformationen bereitstellt:

```groovy
tasks.withType(JavaCompile) {
    options.compilerArgs << "-parameters"
}
```

### 2. XML Darstellung

Damit Spring Boot XML verarbeiten kann, musst du die entsprechende Jackson-XML-Bibliothek `build.gradle` (bei Gradle) hinzufügen.

```jsx
implementation 'com.fasterxml.jackson.dataformat:jackson-dataformat-xml'
```

### 3. Port belegt

Um den Standardport in einer Spring Boot-Anwendung zu ändern, musst du in der Datei `application.properties` den folgenden Eintrag hinzufügen:

```
server.port = NEUER_PORT
```