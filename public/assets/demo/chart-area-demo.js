// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

// Area Chart Example
var ctx = document.getElementById("myAreaChart");
var myLineChart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: ["Apr 1", "May 1", "Jun 1", "Jul 1", "Aug 1", "Sep 1", "Oct 1", "Nov 1", "Dec 1", "Jan 1", "Feb 1"],
    datasets: [{
      label: "Interest rate",
      lineTension: 0.3,
      backgroundColor: "#dd4832",
      borderColor: "#cc1616",
      pointRadius: 5,
      pointBackgroundColor: "#cc1610",
      pointBorderColor: "rgba(255,255,255,0.8)",
      pointHoverRadius: 5,
      pointHoverBackgroundColor: "#cc1610",
      pointHitRadius: 50,
      pointBorderWidth: 2,
      data: [3.15, 3, 3.15, 3.25, 3.15, 3.05, 3.25, 3.5, 3.75, 4, 3.75],
    }],
  },
  options: {
    scales: {
      xAxes: [{
        time: {
          unit: 'date'
        },
        gridLines: {
          display: false
        },
        ticks: {
          maxTicksLimit: 7
        }
      }],
      yAxes: [{
        ticks: {
          min: 2.5,
          max: 4.5,
          maxTicksLimit: 5
        },
        gridLines: {
          color: "rgba(0, 0, 0, .125)",
        }
      }],
    },
    legend: {
      display: false
    }
  }
});
