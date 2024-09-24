package election.services;

import org.springframework.web.bind.annotation.*;

@RestController
public class ElectionConsumer {
    @RequestMapping("/{eID}/consumer")
    @ResponseBody
    public String consumer(@PathVariable int eID) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Election Data Table</title>\n" +
                "    <!-- Include Bootstrap CSS -->\n" +
                "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "    <style>\n" +
                "        .highlight {\n" +
                "            background-color: yellow;\n" +
                "        }\n" +
                "        .search-bar {\n" +
                "            max-width: 300px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container mt-5\">\n" +
                "    <h1 class=\"mb-4\">Election Data</h1>\n" +
                "    <!-- Main Election Data Table -->\n" +
                "    <table id=\"electionTable\" class=\"table table-striped table-bordered\">\n" +
                "        <thead class=\"table-dark\">\n" +
                "            <tr>\n" +
                "                <th>Regions ID</th>\n" +
                "                <th>Name der Region</th>\n" +
                "                <th>Addresse</th>\n" +
                "                <th>Zip Code</th>\n" +
                "                <th>Staat</th>\n" +
                "                <th>Timestamp</th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <tbody></tbody>\n" +
                "    </table>\n" +
                "    <h2 class=\"mb-3\">Partei Data</h2>\n" +
                "    <!-- Search input for filtering candidates by name -->\n" +
                "    <input type=\"text\" id=\"candidateSearch\" onkeyup=\"filterCandidates()\" class=\"form-control search-bar mb-3\" placeholder=\"Suche nach Vorzugskandidaten...\">\n" +
                "    <!-- Buttons to sort the party table by votes -->\n" +
                "    <div class=\"mb-3\">\n" +
                "        <button class=\"btn btn-primary me-2\" onclick=\"sortTableByVotes('desc')\">Sortiere nach höchsten Stimmen</button>\n" +
                "        <button class=\"btn btn-secondary\" onclick=\"sortTableByVotes('asc')\">Sortiere nach niedrigsten Stimmen</button>\n" +
                "    </div>\n" +
                "    <table id=\"partyTable\" class=\"table table-hover table-bordered\">\n" +
                "        <thead class=\"table-dark\">\n" +
                "            <tr>\n" +
                "                <th>Partei</th>\n" +
                "                <th>Nummer der Votes</th>\n" +
                "                <th>Vorzugskandidaten</th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <tbody></tbody>\n" +
                "    </table>\n" +
                "</div>\n" +
                "<script>\n" +
                "console.log(\"Script started\");\n" +
                "fetch('http://localhost:3389/election/" + eID + "/json')\n" +
                "    .then(response => {\n" +
                "        console.log(\"Response status:\", response.status);\n" +
                "        if (!response.ok) {\n" +
                "            throw new Error('Network response was not ok: ' + response.statusText);\n" +
                "        }\n" +
                "        return response.json();\n" +
                "    })\n" +
                "    .then(electionData => {\n" +
                "        console.log(\"Fetched election data:\", electionData);\n" +
                "        renderElectionTable(electionData);\n" +
                "        renderPartyTable(electionData.countingData);\n" +
                "    })\n" +
                "    .catch(error => {\n" +
                "        console.error('Error fetching the election data:', error);\n" +
                "    });\n" +
                "function renderElectionTable(data) {\n" +
                "    const tableBody = document.querySelector('#electionTable tbody');\n" +
                "    const row = document.createElement('tr');\n" +
                "    row.innerHTML = `\n" +
                "        <td>${data.regionID}</td>\n" +
                "        <td>${data.regionName}</td>\n" +
                "        <td>${data.regionAddress}</td>\n" +
                "        <td>${data.regionPostalCode}</td>\n" +
                "        <td>${data.federalState}</td>\n" +
                "        <td>${data.timestamp}</td>\n" +
                "    `;\n" +
                "    tableBody.appendChild(row);\n" +
                "    console.log(\"Main election table rendered\");\n" +
                "}\n" +
                "function renderPartyTable(countingData) {\n" +
                "    const tableBody = document.querySelector('#partyTable tbody');\n" +
                "    countingData.forEach(party => {\n" +
                "        const partyRow = document.createElement('tr');\n" +
                "        const vorzugskandidatenHTML = party.vorzugskandidaten\n" +
                "            ? party.vorzugskandidaten.map(candidate => `\n" +
                "                Listen Nummer: ${candidate.listenNR}, Name: <span class='candidate-name'>${candidate.name}</span>, Stimmen: ${candidate.stimmen}\n" +
                "            `).join('<br>')\n" +
                "            : 'No Candidates';\n" +
                "        partyRow.innerHTML = `\n" +
                "            <td>${party.partyID}</td>\n" +
                "            <td>${party.amountVotes}</td>\n" +
                "            <td class=\"candidates-cell\">${vorzugskandidatenHTML}</td>\n" +
                "        `;\n" +
                "        tableBody.appendChild(partyRow);\n" +
                "    });\n" +
                "    console.log(\"Party table rendered\");\n" +
                "}\n" +
                "// Sort function for party votes\n" +
                "function sortTableByVotes(order) {\n" +
                "    const tableBody = document.querySelector('#partyTable tbody');\n" +
                "    const rows = Array.from(tableBody.querySelectorAll('tr'));\n" +
                "    rows.sort((a, b) => {\n" +
                "        const aVotes = parseInt(a.cells[1].textContent);\n" +
                "        const bVotes = parseInt(b.cells[1].textContent);\n" +
                "        return order === 'desc' ? bVotes - aVotes : aVotes - bVotes;\n" +
                "    });\n" +
                "    tableBody.innerHTML = '';\n" +
                "    rows.forEach(row => tableBody.appendChild(row));\n" +
                "    console.log(`Table sorted in ${order} order`);\n" +
                "}\n" +
                "// Filter and highlight function for candidates\n" +
                "function filterCandidates() {\n" +
                "    const input = document.getElementById('candidateSearch');\n" +
                "    const filter = input.value.toLowerCase();\n" +
                "    const rows = document.querySelectorAll('#partyTable tbody tr');\n" +
                "    rows.forEach(row => {\n" +
                "        const candidatesCell = row.querySelector('.candidates-cell');\n" +
                "        if (candidatesCell) {\n" +
                "            let candidatesText = candidatesCell.textContent.toLowerCase();\n" +
                "            const candidateNameSpans = candidatesCell.querySelectorAll('.candidate-name');\n" +
                "            // Remove previous highlights\n" +
                "            candidateNameSpans.forEach(span => {\n" +
                "                span.innerHTML = span.textContent;\n" +
                "            });\n" +
                "            // Highlight matching text and toggle visibility of rows\n" +
                "            if (candidatesText.includes(filter)) {\n" +
                "                row.style.display = ''; // Show the row\n" +
                "                candidateNameSpans.forEach(span => {\n" +
                "                    const nameText = span.textContent.toLowerCase();\n" +
                "                    if (nameText.includes(filter)) {\n" +
                "                        const regExp = new RegExp(`(${filter})`, 'gi');\n" +
                "                        span.innerHTML = span.textContent.replace(regExp, '<span class=\"highlight\">$1</span>');\n" +
                "                    }\n" +
                "                });\n" +
                "            } else {\n" +
                "                row.style.display = 'none'; // Hide the row if no match\n" +
                "            }\n" +
                "        }\n" +
                "    });\n" +
                "}\n" +
                "</script>\n" +
                "<!-- Include Bootstrap JS and dependencies -->\n" +
                "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js\"></script>\n" +
                "</body>\n" +
                "</html>\n";
    }
}
