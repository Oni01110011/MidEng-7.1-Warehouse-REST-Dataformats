package election.services;

import org.springframework.web.bind.annotation.*;

@RestController
public class ElectionConsumer {
    @RequestMapping("/{eID}/consumer")
    @ResponseBody
    public String consumer( @PathVariable int eID ) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Election Data Table</title>\n" +
                "    <style>\n" +
                "        table {\n" +
                "            width: 100%;\n" +
                "            border-collapse: collapse;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "\n" +
                "        table, th, td {\n" +
                "            border: 1px solid black;\n" +
                "            padding: 10px;\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "\n" +
                "        th {\n" +
                "            background-color: #f2f2f2;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Election Data</h1>\n" +
                "\n" +
                "<!-- Main Election Data Table -->\n" +
                "<table id=\"electionTable\">\n" +
                "    <thead>\n" +
                "        <tr>\n" +
                "            <th>Regions ID</th>\n" +
                "            <th>Name der Region</th>\n" +
                "            <th>Addresse</th>\n" +
                "            <th>Zip Code</th>\n" +
                "            <th>Staat</th>\n" +
                "            <th>Timestamp</th>\n" +
                "        </tr>\n" +
                "    </thead>\n" +
                "    <tbody></tbody>\n" +
                "</table>\n" +
                "\n" +
                "<!-- Nested Party Data Table -->\n" +
                "<h2>Partei Data</h2>\n" +
                "<table id=\"partyTable\">\n" +
                "    <thead>\n" +
                "        <tr>\n" +
                "            <th>Partei</th>\n" +
                "            <th>Nummer der Votes</th>\n" +
                "            <th>Vorzugskandidaten</th>\n" +
                "        </tr>\n" +
                "    </thead>\n" +
                "    <tbody></tbody>\n" +
                "</table>\n" +
                "\n" +
                "<script>\n" +
                "// Debugging: Log a message to indicate that the script has started\n" +
                "console.log(\"Script started\");\n" +
                "\n" +
                "fetch('http://localhost:3389/election/"+ eID +"/json')  // Ensure this URL is correct and accessible\n" +
                "    .then(response => {\n" +
                "        // Debugging: Log the response status\n" +
                "        console.log(\"Response status:\", response.status);\n" +
                "\n" +
                "        if (!response.ok) {\n" +
                "            throw new Error('Network response was not ok: ' + response.statusText);\n" +
                "        }\n" +
                "        return response.json();\n" +
                "    })\n" +
                "    .then(electionData => {\n" +
                "        // Debugging: Log the fetched election data\n" +
                "        console.log(\"Fetched election data:\", electionData);\n" +
                "\n" +
                "        // Call the rendering functions once the data is fetched\n" +
                "        renderElectionTable(electionData);\n" +
                "        renderPartyTable(electionData.countingData);\n" +
                "    })\n" +
                "    .catch(error => {\n" +
                "        // Debugging: Log any errors\n" +
                "        console.error('Error fetching the election data:', error);\n" +
                "    });\n" +
                "\n" +
                "// Function to render the main election table\n" +
                "function renderElectionTable(data) {\n" +
                "    const tableBody = document.querySelector('#electionTable tbody');\n" +
                "\n" +
                "    // Create a row for the election data\n" +
                "    const row = document.createElement('tr');\n" +
                "    row.innerHTML = `\n" +
                "        <td>${data.regionID}</td>\n" +
                "        <td>${data.regionName}</td>\n" +
                "        <td>${data.regionAddress}</td>\n" +
                "        <td>${data.regionPostalCode}</td>\n" +
                "        <td>${data.federalState}</td>\n" +
                "        <td>${data.timestamp}</td>\n" +
                "    `;\n" +
                "\n" +
                "    // Append the row to the table\n" +
                "    tableBody.appendChild(row);\n" +
                "\n" +
                "    // Debugging: Log a message after the main election table is rendered\n" +
                "    console.log(\"Main election table rendered\");\n" +
                "}\n" +
                "\n" +
                "// Function to render the party and candidate table\n" +
                "function renderPartyTable(countingData) {\n" +
                "    const tableBody = document.querySelector('#partyTable tbody');\n" +
                "\n" +
                "    // Loop over each party in the countingData\n" +
                "    countingData.forEach(party => {\n" +
                "        const partyRow = document.createElement('tr');\n" +
                "\n" +
                "        // Check if vorzugskandidaten is null\n" +
                "        const vorzugskandidatenHTML = party.vorzugskandidaten \n" +
                "            ? party.vorzugskandidaten.map(candidate => `\n" +
                "                Listen Nummer: ${candidate.listenNR}, Name: ${candidate.name}, Stimmen: ${candidate.stimmen}\n" +
                "            `).join('<br>')  // Join multiple candidates by line breaks\n" +
                "            : 'No Candidates';  // If null, show \"No Candidates\"\n" +
                "\n" +
                "        // Create the party row\n" +
                "        partyRow.innerHTML = `\n" +
                "            <td>${party.partyID}</td>\n" +
                "            <td>${party.amountVotes}</td>\n" +
                "            <td>${vorzugskandidatenHTML}</td>\n" +
                "        `;\n" +
                "\n" +
                "        // Append the party row to the table\n" +
                "        tableBody.appendChild(partyRow);\n" +
                "    });\n" +
                "\n" +
                "    // Debugging: Log a message after the party table is rendered\n" +
                "    console.log(\"Party table rendered\");\n" +
                "}\n" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
    }
}
