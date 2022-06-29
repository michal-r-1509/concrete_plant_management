const orders_button_tab = "orders_tab";
const management_button_tab = "management_tab";
const clients_button_tab = "clients_tab";
const vehicles_button_tab = "vehicles_tab";
let ordersLoaded = false;
let clientsLoaded = false;

document.getElementById(orders_button_tab).addEventListener('click', ordersLoading);
document.getElementById(management_button_tab).addEventListener('click', managementLoading);
document.getElementById(clients_button_tab).addEventListener('click', clientsLoading);
document.getElementById(vehicles_button_tab).addEventListener('click', vehiclesLoading);

document.getElementById("addOrder").addEventListener('click', orderFormReader);
document.getElementById("addClient").addEventListener('click', clientFormReader);

console.log("zaladowany");

async function orderFormReader() {
    let date = document.querySelector("#date");
    let time = document.querySelector('#time');
    let amount = document.querySelector("#amount");

    if (date.value === "" || time.value === "" || parseFloat(amount.value) <= 0
        || isNaN(parseFloat(amount.value))) {
        console.log("pusto");
        return 0
    } else {
        await saveOrder(date, time, amount);
    }
}

async function clientFormReader() {
    let client_name = document.querySelector("#client_name");
    let street = document.querySelector('#street');
    let post_code = document.querySelector("#post_code");
    let city = document.querySelector("#city");
    let nip = document.querySelector("#nip");

    await saveClient(client_name, street, post_code, city, nip);
}

async function editClientForm() {
    const params = new URLSearchParams(location.search);
    const response = params.has('clients') ? await fetch(`/clients/${params.get('client')}`) : await fetch('/tasks');


    document.querySelector("#client_name").innerHTML = "michal";
    document.querySelector('#street').innerHTML = "pokatna";
    document.querySelector("#post_code").innerHTML = "42-200";
    document.querySelector("#city").innerHTML = "Zadupie";
    document.querySelector("#nip").innerHTML = "5123655779";

    let client_name = document.querySelector("#client_name");
    let street = document.querySelector('#street');
    let post_code = document.querySelector("#post_code");
    let city = document.querySelector("#city");
    let nip = document.querySelector("#nip");

}

async function saveOrder(date, time, amount) {
    let xhr = new XMLHttpRequest();
    let url = "/orders";
    const data = JSON.stringify({"date": date.value, "time": time.value + ':00.000', "amount": amount.value});

    sendingToServer(xhr, url, data);

    xhr.addEventListener("load", e => {
        if (xhr.status === 201) {
            const orderResponse = JSON.parse(xhr.response);
            createOrder(orderResponse);
        }
    });
}

async function saveClient(name, streetAndNo, postCode, city, nip) {
    let xhr = new XMLHttpRequest();
    let url = "/clients";
    const data = JSON.stringify({
        "name": name.value, "streetAndNo": streetAndNo.value,
        "postCode": postCode.value, "city": city.value, "nip": nip.value
    });

    sendingToServer(xhr, url, data);

    xhr.addEventListener("load", e => {
        if (xhr.status === 201) {
            const clientResponse = JSON.parse(xhr.response);
            createClient(clientResponse);
        }
    });
}

function sendingToServer(xhr, url, data) {
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (!(xhr.readyState === 4) && !(xhr.status === 201)) {
            console.log("niewyslano");
        }
    }
    xhr.send(data);
    closingModal();
}

function createOrder({id, status, date, time, amount}) {
    const table = document.getElementById('order_table');
    const tableRow = document.createElement('tr');
    if (ordersLoaded === true) {
        tableRow.innerHTML = `<td><input type="checkbox" ${status ? 'checked' : ''}/></td>
                            <td> ${id} </td>
                            <td>${date}</td>
                            <td>${time}</td>
                            <td>${amount}</td>
                            <td>Zarządzaj</td>
                            <td>Edytuj</td>
                            <td>Usuń</td>`;
        table.appendChild(tableRow);
    }
}

function createClient({id, name, streetAndNo, postCode, city, nip}) {
    const table = document.getElementById('client_table');
    const tableRow = document.createElement('tr');
    if (clientsLoaded === true) {
        tableRow.innerHTML = `
                            <td>${name}</td>
                            <td>${streetAndNo}, ${postCode} ${city}</td>
                            <td>${nip}</td>
                            <td><button class = "clientEditBtn">Edytuj</button></td>
                            <td><button class = "clientDeleteBtn">Usuń</button></td>`;
        table.appendChild(tableRow);
    }
}

async function ordersLoading() {
    if (ordersLoaded === false) {
        ordersLoaded = true;
        const response = await fetch('/orders')
            .then(res => res.json())
            .catch(err => console.error(err));

        for (const resp of response) {
            createOrder(resp);
        }
    }
    buttonDisabling(orders_button_tab);
    buttonEnabling([management_button_tab, clients_button_tab, vehicles_button_tab])
}

async function managementLoading() {
    buttonDisabling(management_button_tab);
    buttonEnabling([orders_button_tab, clients_button_tab, vehicles_button_tab]);
}

async function clientsLoading() {
    if (clientsLoaded === false) {
        clientsLoaded = true;
        const response = await fetch('/clients')
            .then(res => res.json())
            .catch(err => console.error(err));

        for (const resp of response) {
            createClient(resp);
        }
    }
    buttonDisabling(clients_button_tab);
    buttonEnabling([orders_button_tab, management_button_tab, vehicles_button_tab]);
}

async function vehiclesLoading() {
    buttonDisabling(vehicles_button_tab);
    buttonEnabling([orders_button_tab, management_button_tab, clients_button_tab]);
}

function buttonDisabling(buttonId) {
    document.getElementById(buttonId).disabled = 'true';
}

function buttonEnabling(buttonsIds) {
    for (let i = 0; i < buttonsIds.length; i++) {
        document.getElementById(buttonsIds[i]).disabled = '';
    }
}