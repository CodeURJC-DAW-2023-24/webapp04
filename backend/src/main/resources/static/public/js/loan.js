let startIndex = 0;
const chunkSize = 10;

function loadInitData() {
    fetch(`/data?startIndex=${startIndex}&chunkSize=${chunkSize}`)
        .then(response => response.json())
        .then(data => {
            var ctx = document.getElementById('myChart').getContext('2d');
            var labels = Array.from({ length: data.length }, (_, i) => `Period ${i + 1}`);

            var capitalData = data.map(payment => payment.principal);
            var interestData = data.map(payment => payment.interest);

            var remainingAmount = data[0].remainingAmount;

            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Amortised Capital',
                        data: capitalData,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1
                    }, {
                        label: 'Interest',
                        data: interestData,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });

            var tableBody = document.getElementById('loan-table-body');
            data.forEach((payment, index) => {
                var row = `<tr>
                               <td>${index + 1}</td>
                               <td>${payment.principal.toFixed(2)}</td>
                               <td>${payment.interest.toFixed(2)}</td>
                               <td>${(payment.principal + payment.interest).toFixed(2)}</td>
                               <td>${(remainingAmount - (index + 1) * payment.principal).toFixed(2)}</td>
                           </tr>`;
                tableBody.innerHTML += row;
            });
            startIndex += chunkSize;
        });
}

function loadMoreData() {
    fetch(`/data?startIndex=${startIndex}&chunkSize=${chunkSize}`)
        .then(response => response.json())
        .then(data => {
            data.forEach(payment => {
                var row = `<tr>
                               <td>${payment.period}</td>
                               <td>${payment.principal.toFixed(2)}</td>
                               <td>${payment.interest.toFixed(2)}</td>
                               <td>${(payment.principal + payment.interest).toFixed(2)}</td>
                               <td>${payment.remainingAmount.toFixed(2)}</td>
                           </tr>`;
                document.getElementById('loan-table-body').innerHTML += row;
            });
            startIndex += chunkSize;
        });
}

loadInitData();

document.getElementById('loadMoreBtn').addEventListener('click', function() {
    loadMoreData();
});