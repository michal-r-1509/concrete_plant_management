// GENERAL BUTTONS
document.querySelector('nav').addEventListener('click', (ev => {
    if (ev.target.classList.contains('tab_button')) {
        tabTurningOn(ev.target.id);
    }
}));

document.querySelector('aside').addEventListener('click', (ev => {
    if (ev.target.tagName.toLowerCase() === "button") {
        modalTurningOn(ev.target.id);
    }
}));

document.querySelector('body').addEventListener('click', (ev => {
    let button = ev.target;
    if (button.classList.contains('close')) {
        closeButtonHandler();
    } else if (button.id.includes("add")) {
        saveButtonHandler(button.id);
    }
}));

let form = document.getElementsByClassName('data_form');

document.querySelector('section').addEventListener('click', ev => {
    let button = ev.target;
    if (button.id.includes("cl_edt")) {
        clientEditBtnHandler();
        editClientForm(button.getAttribute('id'));
    } else if (button.id.includes("cl_del")) {
        // confirmationWindow();
        let id = idPreparer(button.getAttribute('id'));
        deleteEntity(clientsUrl, id);
        tableRowDeleting('cl_row_' + id);
        closeButtonHandler();
    } else if (button.id.includes("vh_edt")) {
        vehicleEditBtnHandler();
        editVehicleForm(button.getAttribute('id'));
    } else if (button.id.includes("vh_del")) {
        // confirmationWindow();
        let id = idPreparer(button.getAttribute('id'));
        deleteEntity(vehiclesUrl, id);
        tableRowDeleting('vh_row_' + id)
        closeButtonHandler();
    } else if (button.id.includes("or_mng")) {
        orderManagementBtnHandler();
        let id = idPreparer(button.getAttribute('id'));
        createVehicleList();
        managementWindowHandler(id);
    } else if (button.id.includes("or_edt")) {
        orderEditBtnHandler();
        editOrderForm(button.getAttribute('id'));
    } else if (button.id.includes("or_del")) {
        // confirmationWindow();
        let id = idPreparer(button.getAttribute('id'));
        deleteEntity(ordersUrl, id);
        tableRowDeleting('or_row_' + id);
        closeButtonHandler();
    }
})

// ORDER BUTTONS AND VARIABLES
const ordersUrl = '/orders';
const orderModal = document.getElementById('order_modal');
const orders_button_tab = "orders_tab";
let ordersLoaded = false;
let orderAddModalActive = false;

// MANAGEMENT BUTTONS AND VARIABLES
const managementsUrl = '/managements';
const managementModal = document.getElementById('management_modal');
const management_button_tab = "management_tab";
document.getElementById('part_add').addEventListener('click', addPartButtonHandler);
document.getElementById('addPlan').addEventListener('click', () => alert("plan added"));
let temporaryPartList = [];
let partId = 0;

// CLIENT BUTTONS AND VARIABLES
const clientsUrl = '/clients';
const clientModal = document.getElementById('client_modal');
const clients_button_tab = "clients_tab";
let clientsLoaded = false;
let clientAddModalActive = false;

// VEHICLE BUTTONS AND VARIABLES
const vehiclesUrl = '/vehicles';
const vehicleModal = document.getElementById('vehicle_modal');
const vehicles_button_tab = "vehicles_tab";
let vehiclesLoaded = false;
let vehicleAddModalActive = false;

// OTHER BUTTON AND VARIABLES
const confirmationModal = document.getElementById('confirmation_modal');
const overlay = document.getElementById('overlay');
const button_tab_list = [orders_button_tab, management_button_tab, clients_button_tab, vehicles_button_tab];
let actualId = 0;

// BUTTONS HANDLERS

function tabTurningOn(buttonId) {
    switch (buttonId) {
        case "orders_tab": {
            ordersLoading();
            break;
        }
        case "management_tab": {
            managementLoading();
            break;
        }
        case "clients_tab": {
            clientsLoading();
            break;
        }
        case "vehicles_tab": {
            vehiclesLoading();
            break;
        }
    }
}

function modalTurningOn(modalId) {
    switch (modalId) {
        case "order_button": {
            orderModal.style.display = "block";
            overlay.classList.add('active');
            document.getElementById('order_modal_title').innerHTML = "Dodaj zamówienie";
            orderAddModalActive = true;
            createClientList();
            break;
        }
        case "client_button": {
            clientModal.style.display = "block";
            overlay.classList.add('active');
            document.getElementById('client_modal_title').innerHTML = "Dodaj klienta";
            clientAddModalActive = true;
            break;
        }
        case "vehicle_button": {
            vehicleModal.style.display = "block";
            overlay.classList.add('active');
            document.getElementById('vehicle_modal_title').innerHTML = "Dodaj pojazd";
            vehicleAddModalActive = true;
            break;
        }
    }
}

async function closeButtonHandler() {
    orderModal.style.display = "none";
    clientModal.style.display = "none";
    vehicleModal.style.display = "none";
    managementModal.style.display = "none";
    confirmationModal.style.display = "none";
    overlay.classList.remove('active');
    await resetForm();
}

async function saveButtonHandler(saveButtonId) {
    switch (saveButtonId) {
        case "addOrder": {
            if (orderAddModalActive === true) {
                await saveOrder();
            } else {
                await patchOrder(actualId);
                alert("function nie dziala jeszcze");
            }
            break;
        }
        case "addClient": {
            if (clientAddModalActive === true) {
                await saveClient();
            } else {
                await patchClient(actualId);
            }
            break;
        }
        case "addVehicle": {
            if (vehicleAddModalActive === true) {
                saveVehicle();
            } else {
                patchVehicle(actualId);
            }
            break;
        }
    }
}

function orderEditBtnHandler() {
    orderModal.style.display = "block";
    overlay.classList.add('active');
    orderAddModalActive = false;
    createClientList();
    document.getElementById('order_modal_title').innerHTML = "Edytuj zamówienie";
}

function orderManagementBtnHandler() {
    managementModal.style.display = "block";
    overlay.classList.add('active');
}

function clientEditBtnHandler() {
    clientModal.style.display = "block";
    overlay.classList.add('active');
    clientAddModalActive = false;
    document.getElementById('client_modal_title').innerHTML = "Edytuj dane klienta";
}

function vehicleEditBtnHandler() {
    vehicleModal.style.display = "block";
    overlay.classList.add('active');
    vehicleAddModalActive = false;
    document.getElementById('vehicle_modal_title').innerHTML = "Edytuj pojazd";
}

function addPartButtonHandler() {
    partAdding();
}

function confirmationWindow() {
    confirmationModal.style.display = "block";
    overlay.classList.add('active');
}

function tabButtonDisabling(buttonId) {
    let disabledBtn = document.getElementById(buttonId);
    disabledBtn.disabled = 'true';
    for (let i = 0; i < button_tab_list.length; i++) {
        if (buttonId !== button_tab_list[i]) {
            document.getElementById(button_tab_list[i]).disabled = '';
        }
    }
}

// GENERAL

async function closingModal() {
    orderModal.style.display = "none";
    clientModal.style.display = "none";
    vehicleModal.style.display = "none";
    overlay.classList.remove('active');

    await resetForm();

    orderAddModalActive = false;
    clientAddModalActive = false;
    vehicleAddModalActive = false;
}

function resetForm() {
    for (let i = 0; i < form.length; i++) {
        form[i].reset();
    }
    let selectionLists = [document.getElementById('client_selection'),
        document.getElementById('vehicle_selection')];
    for (const element of selectionLists) {
        if (element !== null) {
            element.remove();
        }
    }
    let table = document.getElementById('part_table');
    if (table !== null) {
        table.remove();
    }
    partId = 0;
    temporaryPartList = [];
}

function idPreparer(id) {
    actualId = parseInt(id.toString().substring(7));
    return actualId;
}

function tableRowDeleting(id) {
    document.getElementById(id).remove();
}

// ORDERS

function orderFormReader() {
    // let client = document.querySelector('#client');
    let definedClient = document.querySelector('#client_selection')
    let date = document.querySelector('#date');
    let time = document.querySelector('#time');
    let address = document.querySelector('#address');
    let amount = document.querySelector('#amount');
    let concreteClass = document.querySelector('#conc_class');
    let pump = document.querySelector('#if_pump');
    let description = document.querySelector('#description');

    if (orderAddModalActive === true) {
        return JSON.stringify({
            "date": date.value, "time": time.value + ':00.000',
            "concreteClass": concreteClass.value, "siteAddress": address.value, "pump": pump.checked,
            "description": description.value, "amount": amount.value,
            "client": {"id": definedClient.value, "name": definedClient.options[definedClient.selectedIndex].text}
        });
    } else {
        return JSON.stringify({
            "id": actualId, "date": date.value, "time": time.value + ':00.000',
            "concreteClass": concreteClass.value, "siteAddress": address.value, "pump": pump.checked,
            "description": description.value, "amount": amount.value,
            "client": {"id": definedClient.value, "name": definedClient.options[definedClient.selectedIndex].text}
        });
    }
}

async function saveOrder() {
    const data = orderFormReader();
    const response = await postToServer(ordersUrl, data)
    closingModal();
    createOrder(response);
}

function createOrder(order) {
    const table = document.getElementById('order_table').getElementsByTagName('tbody')[0];
    const tableRow = document.createElement('tr');
    tableRow.setAttribute('id', "or_row_" + order.id)
    if (ordersLoaded === true) {
        tableRow.innerHTML = orderTableRowCreating(order);
        table.appendChild(tableRow);
    }
}

function orderTableRowCreating({id, date, time, amount, concreteClass, siteAddress, pump, status, client}) {
    return `<td><input type="checkbox" ${status ? 'checked' : ''}/></td>
                            <td>${client.name}, ${siteAddress}</td>
                            <td>${id}</td>
                            <td>${date}</td>
                            <td>${time}</td>
                            <td>${amount}</td>
                            <td><button id="or_mng_${id}">Zaplanuj</button></td>
                            <td><button id="or_edt_${id}">Edytuj</button></td>
                            <td><button id="or_del_${id}">Usuń</button></td>`;
}

async function editOrderForm(id) {
    const data = await getEntity(ordersUrl, idPreparer(id));
    await orderModalFill(data);
}

async function orderModalFill({id, date, time, amount, concreteClass, siteAddress, pump, description, status, client}) {
    document.querySelector('#client_selection').value = client.id;
    document.querySelector('#date').value = date;
    document.querySelector('#time').value = time;
    document.querySelector('#address').value = siteAddress;
    document.querySelector('#amount').value = amount;
    document.querySelector('#conc_class').value = concreteClass;
    document.querySelector('#if_pump').checked = pump;
    document.querySelector('#description').value = description;
}

async function ordersLoading() {
    if (ordersLoaded === false) {
        ordersLoaded = true;
        const response = await getEntityList(ordersUrl);
        for (const resp of response) {
            createOrder(resp);
        }
    }
    tabButtonDisabling(orders_button_tab);
}

async function createClientList() {
    const clientSelectedList = document.getElementById('client_list');
    const selectTag = document.createElement("select");
    selectTag.id = 'client_selection';
    let clientList = await getEntityList(clientsUrl);
    for (const client of clientList) {
        let listElement = document.createElement("option");
        listElement.value = client.id;
        listElement.text = client.name;
        selectTag.appendChild(listElement);
    }
    clientSelectedList.appendChild(selectTag);
}

async function patchOrder(id) {
    const data = orderFormReader();
    const response = await patchServerEntity(ordersUrl, id, data);
    closingModal();
    patchOrderRow(response);
}

function patchOrderRow(order) {
    let tableRow = document.getElementById('or_row_' + order.id);
    tableRow.innerHTML = clientTableRowCreating(order);
}

// MANAGEMENTS

async function managementLoading() {
    tabButtonDisabling(management_button_tab);
}

async function managementWindowHandler(id) {
    let order = await getEntity(ordersUrl, id);
    let amountLeft = await leftAmountCalc();
    let entireAmount = parseFloat(order.amount);
    await managementModalFill(order, entireAmount, amountLeft);
}

async function managementModalFill({amount, pump, description}, entireAmount, amountLeft) {
    document.querySelector('#amount_left').innerHTML = (entireAmount - amountLeft).toString();
    document.querySelector('#pump_nec').innerHTML = (pump === false ? "nie" : "tak");
    document.querySelector('#ad_descr').innerHTML = description;
}

function leftAmountCalc() {
    let amountsTable = document.getElementsByClassName('table_amounts');
    let amount = 0;
    for (let i = 0; i < amountsTable.length; i++) {
        amount += parseFloat(amountsTable[i].innerText);
    }
    return amount;
}

function partAdding() {
    let vehicle = document.querySelector('#vehicle_selection');
    let amount = document.querySelector('#part_amount').value;
    let time = document.querySelector('#part_time').value;

    addPartToTemporaryPartList(vehicle.value, amount, time);
    createPart(vehicle.options[vehicle.selectedIndex].text, amount, time);
    footerDescriptionUpdate(amount);
}

function createPart(vehicle, amount, time) {
    const table_container = document.getElementById('table_container');
    let table = document.getElementById("part_table");
    const tableHeader = document.createElement('tr');
    const tableRow = document.createElement('tr');
    if (table === null) {
        table = document.createElement('table');
        table.setAttribute('id', "part_table");
        table.setAttribute('class', 'part_table');
        tableHeader.innerHTML = tableHeaderRowCreating();
        tableHeader.setAttribute('class', 'tr_header');
        table.appendChild(tableHeader);
        table_container.appendChild(table);
    }
    tableRow.innerHTML = partTableRowCreating(vehicle, amount, time);
    table.appendChild(tableRow);
}

function addPartToTemporaryPartList(vehicle, amount, time){
    partId = 0;
    let part = {
        id: partId,
        vehicle: vehicle,
        amount: amount,
        time: time
    };
    partId++;
    temporaryPartList.push(part);
    console.log("array: " + temporaryPartList.length);
}

function tableHeaderRowCreating() {
    return `        <th>pojazd</th>
                    <th>ilość</th>
                    <th>godz. załadunku</th>
                    <th>edycja</th>`
}

function partTableRowCreating(vehicle, amount, time) {
    return `
                            <td>${vehicle}</td>
                            <td class="table_amounts">${amount}</td>
                            <td>${time}</td>
                            <td><button >Usuń</button></td>`;
}

function footerDescriptionUpdate(amount) {
    let amountCounter = parseFloat(document.querySelector('#amount_left').innerHTML);
    let left = amountCounter - amount;
    document.querySelector('#amount_left').innerHTML = left.toString();
}

async function createVehicleList() {
    const vehicleSelectedList = document.getElementById('vehicle_list');
    const selectTag = document.createElement("select");
    selectTag.id = 'vehicle_selection';
    let vehicleList = await getEntityList(vehiclesUrl);
    for (const vehicle of vehicleList) {
        let listElement = document.createElement("option");
        listElement.value = vehicle.id;
        listElement.text = vehicle.type + " " + vehicle.name;
        selectTag.appendChild(listElement);
    }
    vehicleSelectedList.appendChild(selectTag);
}

// id="pt_del_${id}"

// CLIENTS

function clientFormReader() {
    let client_name = document.querySelector("#client_name");
    let street = document.querySelector('#street');
    let post_code = document.querySelector("#post_code");
    let city = document.querySelector("#city");
    let nip = document.querySelector("#nip");
    if (clientAddModalActive === true) {
        return JSON.stringify({
            "name": client_name.value, "streetAndNo": street.value,
            "postCode": post_code.value, "city": city.value, "nip": nip.value
        });
    } else {
        return JSON.stringify({
            "id": actualId, "name": client_name.value, "streetAndNo": street.value,
            "postCode": post_code.value, "city": city.value, "nip": nip.value
        });
    }
}

async function editClientForm(id) {
    const data = await getEntity(clientsUrl, idPreparer(id));
    await clientModalFill(data);
}

function clientModalFill({id, name, streetAndNo, postCode, city, nip}) {
    document.querySelector("#client_name").value = name;
    document.querySelector('#street').value = streetAndNo;
    document.querySelector("#post_code").value = postCode;
    document.querySelector("#city").value = city;
    document.querySelector("#nip").value = nip;
}

async function saveClient() {
    const data = clientFormReader();
    const response = await postToServer(clientsUrl, data)
    closingModal();
    createClient(response);
}

async function patchClient(id) {
    const data = clientFormReader();
    const response = await patchServerEntity(clientsUrl, id, data);
    closingModal();
    patchClientRow(response);
}

function createClient(client) {
    const table = document.getElementById('client_table').getElementsByTagName('tbody')[0];
    const tableRow = document.createElement('tr');
    tableRow.setAttribute('id', "cl_row_" + client.id)
    if (clientsLoaded === true) {
        tableRow.innerHTML = clientTableRowCreating(client);
        table.appendChild(tableRow);
    }
}

function patchClientRow(client) {
    let tableRow = document.getElementById('cl_row_' + client.id);
    tableRow.innerHTML = clientTableRowCreating(client);
}

function clientTableRowCreating({id, name, streetAndNo, postCode, city, nip}) {
    return `
                            <td>${name}</td>
                            <td>${streetAndNo}, ${postCode} ${city}</td>
                            <td>${nip}</td>
                            <td><button id="cl_edt_${id}">Edytuj</button></td>
                            <td><button id="cl_del_${id}">Usuń</button></td>`;
}

async function clientsLoading() {
    if (clientsLoaded === false) {
        clientsLoaded = true;
        const response = await getEntityList(clientsUrl);
        for (const resp of response) {
            createClient(resp);
        }
    }
    tabButtonDisabling(clients_button_tab);
}

// VEHICLES

function vehicleFormReader() {
    let vehicle_name = document.querySelector("#vehicle_name");
    let vehicle_type = document.querySelector('#vehicle_type');
    let capacity = document.querySelector("#capacity");
    let pump_length = document.querySelector("#pump_length");
    let reg_no = document.querySelector("#reg_no");
    let description = document.querySelector('#vehicle_description');

    let capacity_v = vehicle_type.value === "Pompa" ? 0.0 : capacity.value;
    let pump_length_v = vehicle_type.value === "Gruszka" ? 0.0 : pump_length.value;

    if (vehicleAddModalActive === true) {
        return JSON.stringify({
            "name": vehicle_name.value, "type": vehicle_type.value,
            "capacity": capacity_v, "pumpLength": pump_length_v, "regNo": reg_no.value,
            "description": description.value
        });
    } else {
        return JSON.stringify({
            "id": actualId, "name": vehicle_name.value, "type": vehicle_type.value,
            "capacity": capacity_v, "pumpLength": pump_length_v, "regNo": reg_no.value,
            "description": description.value
        });
    }
}

async function editVehicleForm(id) {
    const data = await getEntity(vehiclesUrl, idPreparer(id));
    await vehicleModalFill(data);
}

function vehicleModalFill({id, name, type, capacity, pumpLength, regNo, description}) {
    document.querySelector("#vehicle_name").value = name;
    document.querySelector('#vehicle_type').value = type;
    document.querySelector("#capacity").value = capacity;
    document.querySelector("#pump_length").value = pumpLength;
    document.querySelector("#reg_no").value = regNo;
    document.querySelector('#vehicle_description').value = description;
}

async function saveVehicle() {
    const data = vehicleFormReader();
    const response = await postToServer(vehiclesUrl, data)
    closingModal();
    createVehicle(response);
}

async function patchVehicle(id) {
    const data = vehicleFormReader();
    const response = await patchServerEntity(vehiclesUrl, id, data);
    closingModal();
    patchVehicleRow(response);
}

function createVehicle(vehicle) {
    const table = document.getElementById('vehicle_table').getElementsByTagName('tbody')[0];
    const tableRow = document.createElement('tr');
    tableRow.setAttribute('id', "vh_row_" + vehicle.id)
    if (vehiclesLoaded === true) {
        tableRow.innerHTML = vehicleTableRowCreating(vehicle);
        table.appendChild(tableRow);
    }
}

function patchVehicleRow(vehicle) {
    let tableRow = document.getElementById('vh_row_' + vehicle.id);
    tableRow.innerHTML = vehicleTableRowCreating(vehicle);
}

function vehicleTableRowCreating({id, name, type, capacity, pumpLength, regNo, description}) {
    return `
                            <td>${name}</td>
                            <td>${type}</td>
                            <td>${type === "Pompa" ? "-" : capacity}</td>
                            <td>${type === "Gruszka" ? "-" : pumpLength}</td>
                            <td>${regNo}</td>
                            <td>${description}</td>
                            <td><button id="vh_edt_${id}">Edytuj</button></td>
                            <td><button id="vh_del_${id}">Usuń</button></td>`;
}

async function vehiclesLoading() {
    if (vehiclesLoaded === false) {
        vehiclesLoaded = true;
        const response = await getEntityList(vehiclesUrl);
        for (const resp of response) {
            createVehicle(resp);
        }
    }
    tabButtonDisabling(vehicles_button_tab);
}

// SERVER OPERATIONS

async function postToServer(url, data) {
    return await fetch(url, {
        method: 'POST',
        body: data,
        headers: {'Content-Type': 'application/json'}
    })
        .then(resp => {
            return resp.json()
        });
}

async function patchServerEntity(url, id, data) {
    return await fetch(url + '/' + id, {
        method: 'PUT',
        body: data,
        headers: {'Content-Type': 'application/json'}
    })
        .then(resp => {
            return resp.json()
        });
}

async function getEntityList(url) {
    return await fetch(url)
        .then(res => res.json())
        .catch(err => console.error(err));
}

async function getEntity(url, id) {
    return await fetch(url + '/' + id)
        .then(data => {
            return data.json()
        })
        .catch(err => console.error(err));
}

async function deleteEntity(url, id) {
    await fetch(url + '/' + id, {method: 'DELETE'});
}

/*function loadScript(url) {
    const head = document.getElementsByTagName('head')[0];
    const script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = url;
    head.appendChild(script);
}

loadScript("connection_script.js");*/


