function loadMoreAccounts(startIndex, chunkSize) {
    fetch(`/account_load?startIndex=${startIndex}&chunkSize=${chunkSize}`)
        .then(response => response.json())
        .then(data => {
            const accountsContainer = document.querySelector('.accounts');
            data.forEach(account => {
                const accountHtml = `
                    <div class="account">
                        <dl class="account-details">
                            <div>
                                <dt>${account.nip}</dt>
                                <dd>NIP</dd>
                            </div>
                            <div>
                                <dt>${account.iban}</dt>
                                <dd>IBAN</dd>
                            </div>
                            <div>
                                <dt>${account.name}</dt>
                                <dd>Name</dd>
                            </div>
                            <div>
                                <dt>${account.surname}</dt>
                                <dd>Surname</dd>
                            </div>
                            <div>
                                <img src="data:image/jpeg;base64,${account.imageBase64}" style="width: 50px; height: 50px;"/>
                            </div>
                        </dl>
                    </div>`;
                accountsContainer.insertAdjacentHTML('beforeend', accountHtml);
            });
        });
}

document.addEventListener('DOMContentLoaded', () => {
    loadMoreAccounts(0, 10);
});

document.getElementById('account_button').addEventListener('click', () => {
    const startIndex = document.querySelectorAll('.account').length;
    const chunkSize = 10;

    loadMoreAccounts(startIndex, chunkSize);
});
