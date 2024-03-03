// Función para cargar más transferencias
function loadMoreTransfers(startIndex, chunkSize) {
    fetch(`/profile_data?startIndex=${startIndex}&chunkSize=${chunkSize}`)
        .then(response => response.json())
        .then(data => {
            const transfersContainer = document.querySelector('.transfers');

            data.forEach(transfer => {
                const transferHtml = `
                    <div class="transfer">
                        <div class="transfer-logo">
                            <img src="https://assets.codepen.io/285131/apple.svg" />
                        </div>
                        <dl class="transfer-details">
                            <div>
                                <dt>${transfer.senderIBAN}</dt>
                                <dd>Sender</dd>
                            </div>
                            <div>
                                <dt>${transfer.receiverIBAN}</dt>
                                <dd>Receiver</dd>
                            </div>
                            <div>
                                <dt>${transfer.date}</dt>
                                <dd>Date payment</dd>
                            </div>
                            <div>
                                <dt>${transfer.transferType}</dt>
                                <dd>T Type</dd>
                            </div>
                        </dl>
                        <div class="transfer-number">${transfer.amount} €</div>
                    </div>`;
                transfersContainer.insertAdjacentHTML('beforeend', transferHtml);
            });
        });
}

// Cargar por defecto 10 elementos
document.addEventListener('DOMContentLoaded', () => {
    loadMoreTransfers(0, 10);
});

// Event listener para el botón "Load more"
document.getElementById('profile_button').addEventListener('click', () => {
    const startIndex = document.querySelectorAll('.transfer').length;
    const chunkSize = 10;

    loadMoreTransfers(startIndex, chunkSize);
});
