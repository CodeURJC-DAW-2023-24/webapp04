// Función para cargar más transferencias
function loadMoreTransfers(startIndex, chunkSize) {
    fetch(`/transfers_manager_load?startIndex=${startIndex}&chunkSize=${chunkSize}`)
        .then(response => response.json())
        .then(data => {
            const transfersContainer = document.querySelector('.transfers');

            data.forEach(transfer => {
                const transferHtml = `
                    <div class="transfer">
                        <dl class="transfer-details">
                            <div class="max">
                                <dt>Sender</dt>
                                <dd>${transfer.senderIBAN}</dd>
                            </div>
                            <div class="max">
                                <dt>Receiver</dt>
                                <dd>${transfer.receiverIBAN}</dd>
                            </div>
                            <div class="max">
                                <dt>Date payment</dt>
                                <dd>${transfer.date}</dd>
                            </div>
                            <div class="min">
                                <dt>T Type</dt>
                                <dd>${transfer.transferType}</dd>
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
document.getElementById('transfer_button').addEventListener('click', () => {
    const startIndex = document.querySelectorAll('.transfer').length;
    const chunkSize = 10;

    loadMoreTransfers(startIndex, chunkSize);
});
