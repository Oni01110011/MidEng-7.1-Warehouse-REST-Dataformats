# MidEng 7.3 Erweiterung
**von**: Bernhard Aichinger-Ganas  
**am**: 24.10.24

## Einführung

Mit Blick auf die bevorstehende Nationalratswahl wurde ein System entwickelt, um Wahlergebnisse schnell und effizient von den Wahllokalen zu aggregieren. Das System kombiniert REST-Schnittstellen mit Kafka-Integration, um Daten in Echtzeit zu übertragen und Rückmeldungen bereitzustellen. Unterstützte Formate sind JSON und XML.

---

## Architekturübersicht

Das System besteht aus mehreren Hauptkomponenten:
1. **REST-Schnittstelle**: Bereitgestellt durch `ElectionController` für die Übermittlung und Abfrage von Wahldaten.
2. **Kafka-basierte Datenübertragung**: Ermöglicht durch `ElectionProducer`, `ElectionConsumer` und `ElectionFeedbackProducer`.
3. **Simulation**: Generierung und Übertragung von simulierten Wahldaten mit `ElectionSimulator` und `SimulationRunner`.
4. **Service-Schicht**: Aggregation und Bereitstellung von Wahldaten mit `ElectionService`.

---

## Komponenten im Detail

### 1. **REST-Schnittstelle**

#### ElectionController

- **Endpunkte**:
    - **POST `/election2024/send`**:
        - Funktion: Senden von Wahldaten.
        - Eingabe: JSON-Objekt des Typs `ElectionData`.
        - Implementierung: Daten werden serialisiert und mit `ElectionProducer` an Kafka gesendet.

    - **GET `/election2024/results/json`**:
        - Funktion: Liefert aggregierte Wahldaten im JSON-Format.
        - Implementierung: Daten aus `ElectionService` werden als JSON serialisiert.

    - **GET `/election2024/results/xml`**:
        - Funktion: Liefert aggregierte Wahldaten im XML-Format.
        - Implementierung: Daten aus `ElectionService` werden als XML serialisiert.

---

### 2. **Kafka-Integration**

#### ElectionProducer

- **Funktion**: Sendet Wahldaten an das Kafka-Topic `election_topic`.
- **Implementierung**:
  ```java
  kafkaTemplate.send(TOPIC, data);
  ```
- **Datei**: `ElectionProducer.java`.

#### ElectionConsumer

- **Funktion**: Empfängt Wahldaten von Kafka, verarbeitet sie und speichert sie in `ElectionService`. Anschließend wird ein Feedback über `ElectionFeedbackProducer` gesendet.
- **Datei**: `ElectionConsumer.java`.

#### ElectionFeedbackProducer

- **Funktion**: Sendet Feedback zur Datenverarbeitung an das Kafka-Topic `election_feedback`.
- **Datei**: `ElectionFeedbackProducer.java`.

#### FeedbackListener

- **Funktion**: Lauscht auf das Topic `election_feedback` und protokolliert eingehende Rückmeldungen.
- **Datei**: `FeedbackListener.java`.

---

### 3. **Datenmodell**

#### ElectionData

- **Funktion**: Repräsentiert die Wahldaten einer Region.
- **Attribute**:
    - `pollingStationId`: ID des Wahllokals.
    - `regionID`, `regionName`, `regionAddress`, `regionPostalCode`, `federalState`: Regionale Metadaten.
    - `countingData`: Liste von `Party`-Objekten mit Stimmenzahlen und Vorzugskandidaten.
- **Datei**: Definiert in den Modellklassen.

#### Party

- **Funktion**: Repräsentiert eine Partei und ihre Stimmenanzahl sowie Vorzugskandidaten.
- **Attribute**:
    - `partyID`: ID der Partei.
    - `amountVotes`: Anzahl der erhaltenen Stimmen.
    - `vorzugskandidaten`: Liste von `Vorzugskandidaten`.

#### Vorzugskandidaten

- **Funktion**: Repräsentiert einen Vorzugskandidaten einer Partei.
- **Attribute**:
    - `name`: Name des Kandidaten.
    - `listenNR`: Listenplatz des Kandidaten.
    - `stimmen`: Anzahl der Stimmen.

---

### 4. **Simulation**

#### ElectionSimulator

- **Funktion**: Generiert Wahldaten für Parteien und Vorzugskandidaten mit zufälligen Stimmenzahlen.
- **Implementierung**:
    - Zufallszahlen: `generateRandomNumber()` erzeugt Werte zwischen 0 und 500.
    - Beispiel für erzeugte Parteien:
      ```java
      ArrayList<Vorzugskandidaten> oevpCandidates = new ArrayList<>();
      oevpCandidates.add(new Vorzugskandidaten("OEVP", 212, "Danilo Stoilovski", generateRandomNumber()));
      ```
    - Parteien und Kandidaten werden zu `ElectionData` zusammengefasst.
- **Datei**: `ElectionSimulator.java`.

#### SimulationRunner

- **Funktion**: Führt Simulationen aus und sendet die generierten Wahldaten an Kafka.
- **Datei**: `SimulationRunner.java`.

---

### 5. **Service-Schicht**

#### ElectionService

- **Funktion**: Aggregiert Wahldaten von verschiedenen Wahllokalen und stellt sie in JSON und XML bereit.
- **Methoden**:
    - `addData(ElectionData data)`: Fügt neue Daten hinzu.
    - `getAggregatedDataAsJson()`: Serialisiert die Daten als JSON.
    - `getAggregatedDataAsXml()`: Serialisiert die Daten als XML.
- **Datei**: `ElectionService.java`.

#### ElectionDataSender

- **Funktion**: Serialisiert simulierte Wahldaten und sendet sie an Kafka.
- **Datei**: `ElectionDataSender.java`.

---

## Workflow

1. **Datenübertragung**:
    - Wahldaten werden simuliert und mit `ElectionProducer` an Kafka gesendet.
    - `ElectionConsumer` empfängt die Daten, speichert sie und sendet Feedback.
2. **Datenaggregation**:
    - `ElectionService` sammelt und aggregiert die Wahldaten.
3. **Abfrage der Ergebnisse**:
    - Ergebnisse können als JSON oder XML über die REST-Endpoints abgefragt werden.
4. **Feedback-Verarbeitung**:
    - Feedback wird von `FeedbackListener` empfangen und protokolliert.

---

## Konfiguration

### Kafka Topics

- **`election_topic`**: Überträgt Wahldaten.
- **`election_feedback`**: Überträgt Rückmeldungen.

### Anpassung des Server-Ports

- **Datei**: `application.properties`:
  ```properties
  server.port = NEUER_PORT
  ```

### Abhängigkeiten

- Spring Boot
- Kafka
- Jackson (für JSON und XML)

---

## Beispiel-Codeausführungen

### Start der Anwendung

1. Anwendung starten:
   ```bash
   java -jar ElectionApp.jar
   ```

2. Ergebnisse abrufen:
    - JSON: `http://localhost:8080/election2024/results/json`
    - XML: `http://localhost:8080/election2024/results/xml`

3. Simulierte Daten werden automatisch über `SimulationRunner` generiert und gesendet.

## Fragen 

### **1. Eigenschaften der Message Oriented Middleware (MOM):**
- **Asynchrone Kommunikation:** MOM ermöglicht es, Nachrichten zwischen Anwendungen asynchron zu versenden und zu empfangen. Anwendungen müssen nicht gleichzeitig aktiv sein.
- **Entkopplung:** Sender und Empfänger müssen nicht direkt miteinander verbunden sein. Sie kommunizieren über die Middleware, was die Skalierbarkeit erhöht.
- **Zuverlässigkeit:** MOM stellt sicher, dass Nachrichten sicher und vollständig zugestellt werden, auch bei Systemausfällen.
- **Nachrichtenpersistenz:** Nachrichten können persistiert werden, um sicherzustellen, dass sie auch bei Ausfällen der Infrastruktur nicht verloren gehen.
- **Skalierbarkeit:** MOM kann bei wachsender Last zusätzliche Ressourcen nutzen und skaliert horizontal und vertikal.
- **Nachrichtenpriorisierung:** Nachrichten können nach Priorität behandelt werden, um kritische Inhalte bevorzugt zu verarbeiten.

---

### **2. Transiente und synchrone Kommunikation:**
- **Transiente Kommunikation:**
    - Nachrichten werden nur für die Dauer der Verbindung im System gehalten.
    - Wenn der Empfänger zum Zeitpunkt des Sendens nicht verfügbar ist, geht die Nachricht verloren.
    - Beispiel: Direktes Socket-basierte Kommunikation.

- **Synchrone Kommunikation:**
    - Der Sender wartet auf eine Antwort des Empfängers, bevor er fortfährt.
    - Der Prozess ist blockierend und beide Parteien müssen zur gleichen Zeit aktiv sein.
    - Beispiel: Remote Procedure Call (RPC).

---

### **3. Funktionsweise einer JMS Queue:**
Eine **JMS Queue** wird für die Punkt-zu-Punkt-Kommunikation genutzt, wobei die Nachrichten einem einzelnen Empfänger zugestellt werden:
1. Der Sender sendet eine Nachricht in die Queue.
2. Die Middleware speichert die Nachricht in der Queue, bis sie von einem Empfänger abgeholt wird.
3. Ein Consumer holt die Nachricht ab, wodurch sie aus der Queue entfernt wird.
4. Es gibt immer genau einen Empfänger für jede Nachricht.
    - Falls kein Consumer verfügbar ist, bleibt die Nachricht in der Queue, bis sie abgeholt wird.

---

### **4. JMS Overview – Wichtige Klassen und deren Zusammenhang:**
- **`ConnectionFactory`:** Erzeugt Verbindungen zu einem Message Broker.
- **`Connection`:** Repräsentiert eine Verbindung zwischen dem Client und dem Broker.
- **`Session`:** Bietet eine Transaktionsumgebung, um Nachrichten zu senden und zu empfangen.
- **`Destination`:** Abstrakte Klasse, die entweder eine Queue (Punkt-zu-Punkt) oder ein Topic (Publish-Subscribe) repräsentiert.
- **`MessageProducer`:** Zum Senden von Nachrichten an eine Queue oder ein Topic.
- **`MessageConsumer`:** Zum Empfangen von Nachrichten aus einer Queue oder einem Topic.
- **`Message`:** Die eigentliche Datenstruktur, die gesendet und empfangen wird.

Zusammenhang:
1. Ein Client erstellt über die **ConnectionFactory** eine Verbindung.
2. Die Verbindung erzeugt eine **Session**.
3. Die Session verwaltet **Producers** und **Consumers**, die mit einem spezifischen **Destination** (Queue oder Topic) verbunden sind.

---

### **5. Funktionsweise eines JMS Topic:**
Ein **JMS Topic** wird im Publish-Subscribe-Modell verwendet:
1. Ein Producer veröffentlicht eine Nachricht auf einem Topic.
2. Alle aktiven Subscriber, die auf dieses Topic registriert sind, erhalten eine Kopie der Nachricht.
3. Es können mehrere Empfänger gleichzeitig existieren, und jeder bekommt die Nachricht unabhängig voneinander.
4. Persistent Topics speichern Nachrichten für registrierte Abonnenten, auch wenn sie vorübergehend nicht verfügbar sind.

---

### **6. Lose gekoppeltes verteiltes System:**
- **Definition:** Ein lose gekoppeltes System besteht aus Komponenten, die unabhängig voneinander arbeiten und nur über definierte Schnittstellen oder Nachrichtenprotokolle kommunizieren.
- **Beispiel:** Microservices-Architektur, bei der Services über REST-APIs oder MOM kommunizieren.
- **Warum "lose"?**
    - Sender und Empfänger sind nicht direkt voneinander abhängig.
    - Änderungen an einer Komponente erfordern keine Änderungen an den anderen.
    - Systeme sind robuster und leichter wartbar, da die Interaktion über standardisierte Schnittstellen erfolgt.