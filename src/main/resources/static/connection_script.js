document.getElementById("addOrder").addEventListener('click', formReader);

console.log("zaladowany");

async function formReader() {
    let date = document.querySelector("#date");
    let time = document.querySelector('#time');
    let amount = document.querySelector("#amount");

    if (date.value === "" || time.value === "" || parseFloat(amount.value) <= 0
        || isNaN(parseFloat(amount.value))) {
        console.log("pusto");
        return 0
    } else {
        //document.getElementById("message").innerHTML = "";
        await sendJSON(date, time, amount);
    }
}

async function sendJSON(date, time, amount) {
    let xhr = new XMLHttpRequest();
    let url = "/orders";
    // let result = document.querySelector('#message');

    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (!(xhr.readyState === 4) && !(xhr.status === 201)) {
            // result.innerHTML = this.responseText;
            console.log("niewyslano");
        }
    }
    const data = JSON.stringify({"date": date.value, "time": time.value + ':00.000', "amount": amount.value});
    xhr.send(data);
    closingModal();

    xhr.addEventListener("load", e => {
        if (xhr.status === 201) {
            const orderResponse = JSON.parse(xhr.response);
            createOrder(orderResponse);
        }
    });
}

function createOrder({id, status, date, time, amount}) {
    const table = document.getElementById('order_table');
    const tableRow = document.createElement('tr');
    tableRow.innerHTML = `<td>${status}</td>
                            <td> ${id} </td>
                            <td>${date}</td>
                            <td>${time}</td>
                            <td>${amount}</td>
                            <td>Edycja</td>
                            <td>Usuń</td>`;
    table.appendChild(tableRow);
}