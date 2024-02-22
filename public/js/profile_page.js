let currentPage = 0;
const itemsPerPage = 10;

function loadTransfersData() {
    let transfers = document.getElementById("transfers");
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let transfersData = JSON.parse(xhr.responseText).slice(currentPage * itemsPerPage, (currentPage + 1) * itemsPerPage);

            transfersData.forEach(example => {
                let transferContent = document.createElement("div");
                transferContent.classList.add("transfer");

                let transferLogo = document.createElement("div");
                let image = document.createElement("img");
                image.src = example.image;
                transferLogo.classList.add("transfer-logo");
                transferLogo.appendChild(image);

                let transferDetails = document.createElement("dl");
                transferDetails.classList.add("transfer-details");
                let transferNameDetails = document.createElement("div");
                let dtND = document.createElement("dt");
                dtND.textContent = example.nameAccount;
                transferNameDetails.appendChild(dtND);
                let ddND = document.createElement("dd");
                ddND.textContent = example.paymentName;
                transferNameDetails.appendChild(ddND);

                let transferAccountDetails = document.createElement("div");
                let dtAD = document.createElement("dt");
                dtAD.textContent = example.lastDigits;
                transferAccountDetails.appendChild(dtAD);
                let ddAD = document.createElement("dd");
                ddAD.textContent = "Last four digits";
                transferAccountDetails.appendChild(ddAD);

                let transferDateDetails = document.createElement("div");
                let dtDD = document.createElement("dt");
                dtDD.textContent = example.datePayment;
                transferDateDetails.appendChild(dtDD);
                let ddDD = document.createElement("dd");
                ddDD.textContent = "Date payment";
                transferDateDetails.appendChild(ddDD);

                let paymentAmount = document.createElement("div");
                paymentAmount.classList.add("transfer-number");
                paymentAmount.textContent = `- ${example.amount} $`;

                transferContent.appendChild(transferLogo);
                transferContent.appendChild(transferDetails);
                transferDetails.appendChild(transferNameDetails);
                transferDetails.appendChild(transferAccountDetails);
                transferDetails.appendChild(transferDateDetails);
                transferDetails.appendChild(paymentAmount);
                transfers.appendChild(transferContent);
            });
        }
    };
    xhr.open("GET", "server/endpoint?start=" + currentPage * itemsPerPage + "&limit=" + itemsPerPage, true);
    xhr.send();
}

function loadMoreItems() {
    currentPage++;
    loadTransfersData();
}

window.onload = loadTransfersData();
