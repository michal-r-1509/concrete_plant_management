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
    tableButtonsHandler(ev.target);
})

const sortButton = document.getElementById('sort_confirm');
sortButton.addEventListener('click', sortHandler);

document.getElementById('display_form').addEventListener('change', ev => {
    displayOrderRowsHandler(ev.target.id);
});

// ORDER BUTTONS AND VARIABLES
const ordersUrl = '/orders';
const orderModal = document.getElementById('order_modal');
const orders_button_tab = "orders_tab";
let orderAddModalActive = false;
const ordersTableName = "order_table";

// MANAGEMENT BUTTONS AND VARIABLES
const managementsUrl = '/order_batches';
const managementModal = document.getElementById('management_modal');
const management_button_tab = "management_tab";
document.getElementById('part_add').addEventListener('click', addPartButtonHandler);
document.getElementById('addPlan').addEventListener('click', () => {
    addingPlanHandler();
});
document.getElementById("table_container").addEventListener('click', ev => {
    if (ev.target.tagName.toUpperCase() === 'BUTTON') {
        deletePartButtonHandler(ev.target);
    }
});
let managementAddModalActive = false;
const managementsTableName = "management_table";
const partTableName = "part_table";
let temporaryBatchList = [];
let partId = 0;
let vehicleAmountSum = 0;
let orderAmount = 0;
let temporaryOrder = null;

// CLIENT BUTTONS AND VARIABLES
const clientsUrl = '/clients';
const clientModal = document.getElementById('client_modal');
const clients_button_tab = "clients_tab";
let clientAddModalActive = false;
const clientsTableName = "client_table";

// VEHICLE BUTTONS AND VARIABLES
const vehiclesUrl = '/vehicles';
const vehicleModal = document.getElementById('vehicle_modal');
const vehicles_button_tab = "vehicles_tab";
const vehiclesTableName = "vehicle_table";
let vehicleAddModalActive = false;

// ARCHIVE BUTTONS AND VARIABLES
const archivesUrl = '/archives';
const archive_button_tab = "archive_tab";
const archiveTableName = "archive_table";

// OTHER BUTTON AND VARIABLES
const informationModal = document.getElementById('information_modal');
const overlay = document.getElementById('overlay');
const button_tab_list = [orders_button_tab, management_button_tab, clients_button_tab,
    vehicles_button_tab, archive_button_tab];
let actualId = 0;
const tableContainer = document.getElementById('tables_main_container');
const sortContainer = "sort_container"
const footerSortOptionsContainer = document.getElementById(sortContainer);
const footerDisplayOptionsContainer = document.getElementById('display_container');
let displayState = "";
let displayStateUrl = "";

// BUTTONS HANDLERS

async function tabTurningOn(buttonId) {
    displayOptionsHandler(false);
    switch (buttonId) {
        case "orders_tab": {
            await tableCreating(ordersTableName);
            await sortOptionsCreating(ordersTableName);
            displayOptionsHandler(true);
            await ordersLoading(ordersUrl);
            break;
        }
        case "management_tab": {
            await tableCreating(managementsTableName);
            await sortOptionsCreating(managementsTableName);
            await managementsLoading(managementsUrl);
            break;
        }
        case "clients_tab": {
            await tableCreating(clientsTableName);
            await sortOptionsCreating(clientsTableName);
            await clientsLoading(clientsUrl);
            break;
        }
        case "vehicles_tab": {
            await tableCreating(vehiclesTableName);
            await sortOptionsCreating(vehiclesTableName);
            await vehiclesLoading(vehiclesUrl);
            break;
        }
        case "archive_tab":{
            await tableCreating(archiveTableName);
            await sortOptionsCreating(archiveTableName);
            await archiveLoading(archivesUrl);
            break;
        }
    }
}

async function tableButtonsHandler(element) {
    let id;
    if (element.id.includes("_")){
        id = idPreparer(element.getAttribute('id'));
    }
    if (element.id.includes("or_mng")) {
        orderManagementBtnHandler();
        await managementWindowOpen(id);
    } else if (element.id.includes("or_edt")) {
        await orderEditBtnHandler();
        await editOrderForm(element.getAttribute('id'));
    } else if (element.id.includes("or_del")) {
        await deleteEntity(ordersUrl, id);
        tableRowDeleting('or_row_' + id);
    } else if (element.id.includes("or_arc")) {
        await archiveOrder(archivesUrl, id);
        await deleteEntity(ordersUrl, id);
        tableRowDeleting('or_row_' + id);
    } else if (element.id.includes("mg_del")) {
        await deleteEntity(managementsUrl, id);
        tableRowDeleting('mg_row_' + id);
    }else if (element.id.includes("mg_prt")) {
        await printHandler(id);
    } else if (element.id.includes("cl_edt")) {
        clientEditBtnHandler();
        await editClientForm(element.getAttribute('id'));
    } else if (element.id.includes("cl_del")) {
        let status = await deleteEntity(clientsUrl, id);
        if (status) {
            tableRowDeleting('cl_row_' + id);
        }else {
            informationWindow();
        }
    } else if (element.id.includes("vh_edt")) {
        vehicleEditBtnHandler();
        await editVehicleForm(element.getAttribute('id'));
    } else if (element.id.includes("vh_del")) {
        let status = await deleteEntity(vehiclesUrl, id);
        if (status) {
            tableRowDeleting('vh_row_' + id);
        } else {
            informationWindow();
        }
    }else if (element.id.includes("sts")){
        await statusCheckboxHandler(element);
    }
}

async function modalTurningOn(modalId) {
    switch (modalId) {
        case "order_button": {
            orderModal.style.display = "block";
            overlay.classList.add('active');
            document.getElementById('order_modal_title').innerHTML = "Dodaj zamówienie";
            orderAddModalActive = true;
            await createClientList();
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
    informationModal.style.display = "none";
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
                await saveVehicle();
            } else {
                await patchVehicle(actualId);
            }
            break;
        }
    }
}

async function orderEditBtnHandler() {
    orderModal.style.display = "block";
    overlay.classList.add('active');
    orderAddModalActive = false;
    await createClientList();
    document.getElementById('order_modal_title').innerHTML = "Edytuj zamówienie";
}

function orderManagementBtnHandler() {
    managementModal.style.display = "block";
    overlay.classList.add('active');
    managementAddModalActive = false;
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

async function addPartButtonHandler() {
    if (vehicleAmountSum <= orderAmount) {
        await partFormReader();
    }
}

async function deletePartButtonHandler(button) {
    let rowId = idPreparer(button.id);
    temporaryBatchList[rowId - 1].amount = 0;
    temporaryBatchList[rowId - 1].toDelete = true;
    button.parentElement.parentElement.remove();
    orderBatchAmountSum();
    footerDescriptionUpdate();
}

function orderBatchAmountSum() {
    vehicleAmountSum = 0.0;
    for (let i = 0; i < temporaryBatchList.length - 1; i++) {
        vehicleAmountSum += temporaryBatchList[i].amount;
    }
}

function informationWindow() {
    informationModal.style.display = "block";
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

async function sortHandler() {
    let sortOption = footerSortOptionsContainer.firstElementChild;
    let tableName = sortOption.id.substring(5);
    await tableRemoving();
    await tableCreating(tableName);
    switch (tableName) {
        case ordersTableName: {
            await ordersLoading(ordersUrl + displayStateUrl + "?sort=" + sortOption.value + displayState);
            break;
        }
        case managementsTableName: {
            await managementsLoading(managementsUrl + "?sort=" + sortOption.value);
            break;
        }
        case clientsTableName: {
            await clientsLoading(clientsUrl + "?sort=" + sortOption.value);
            break;
        }
        case vehiclesTableName: {
            await vehiclesLoading(vehiclesUrl + "?sort=" + sortOption.value);
            break;
        }
        case archiveTableName: {
            await archiveLoading(archivesUrl + "?sort=" + sortOption.value);
            break;
        }
    }
}

async function statusCheckboxHandler(checkbox) {
    let rowId = (checkbox.parentElement.parentElement).id;
    let id = idPreparer(checkbox.getAttribute('id'));
    let isChecked = checkbox.checked;
    if (checkbox.id.includes("pt_sts")) {
        let response = await orderBatchStatusHandler(id);
        editButtonsDisplay(rowId, isChecked);
    }
}

function editButtonsDisplay(rowId, status){
    let buttons = document.getElementById(rowId).getElementsByTagName('button');
    for (let i = 0; i < buttons.length; i++) {
        if (buttons[i].id.includes("or_del")){
            buttons[i].textContent = status ? "archiwizuj" : "usuń";
            buttons[i].id = buttons[i].id.replace("del", "arc");
            continue;
        }
        if (buttons[i].id.includes("or_arc")){
            buttons[i].textContent = status ? "archiwizuj" : "usuń";
            buttons[i].id = buttons[i].id.replace("arc", "del");
            continue;
        }
        buttons[i].disabled = status;
    }
}

function displayOptionsHandler(bool){
    footerDisplayOptionsContainer.style.display = bool ? "block" : "none";
}

// GENERAL

async function closingModal() {
    orderModal.style.display = "none";
    clientModal.style.display = "none";
    vehicleModal.style.display = "none";
    managementModal.style.display = "none";
    overlay.classList.remove('active');

    await resetForm();

    orderAddModalActive = false;
    managementAddModalActive = false;
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
    let table = document.getElementById(partTableName);
    if (table !== null) {
        table.remove();
    }
    partId = 0;
    temporaryBatchList = [];
    vehicleAmountSum = 0;
    orderAmount = 0;
    temporaryOrder = null;
}

function idPreparer(id) {
    actualId = parseInt(id.toString().substring(7));
    return actualId;
}

function tableRowDeleting(id) {
    document.getElementById(id).remove();
}

function tableRemoving() {
    let newTable = tableContainer.firstElementChild;
    if (newTable !== null) {
        newTable.remove();
    }
}

function displayOrderRowsHandler(id){
    if (id.includes("display_done")){
        displayState = "&done=true";
        displayStateUrl = "/search"
    }else if (id.includes("display_undone")){
        displayState = "&done=false";
        displayStateUrl = "/search"
    }else{
        displayState = "";
        displayStateUrl = "";
    }
}

// ORDERS

async function ordersLoading(url) {
    const response = await getEntityList(url);
    for (const resp of response) {
        createOrder(resp);
    }
    tabButtonDisabling(orders_button_tab);
}

function orderFormReader() {
    let definedClient = document.querySelector('#client_selection')
    let date = document.querySelector('#date');
    let time = document.querySelector('#time');
    let address = document.querySelector('#address');
    let amount = document.querySelector('#amount');
    let concreteClass = document.querySelector('#conc_class');
    let pump = document.querySelector('#if_pump');
    let description = document.querySelector('#description');

    return JSON.stringify({
        "id": orderAddModalActive ? "" : actualId, "date": date.value, "time": time.value,
        "concreteClass": concreteClass.value, "siteAddress": address.value, "pump": pump.checked,
        "description": description.value, "amount": amount.value,
        "client": {"id": definedClient.value, "name": definedClient.options[definedClient.selectedIndex].text}
    });
}

async function saveOrder() {
    const data = orderFormReader();
    const response = await postToServer(ordersUrl, data);
    if (response !== null) {
        await closingModal();
        createOrder(response);
    }
}

function createOrder(order) {
    const table = document.getElementById(ordersTableName);
    if (table === null){
        return;
    }
    const tableRow = document.createElement('tr');
    tableRow.setAttribute('id', "or_row_" + order.id);
    tableRow.innerHTML = orderTableRowCreating(order);
    table.appendChild(tableRow);
}

function orderTableRowCreating({id, date, time, amount, siteAddress, done, pump, client}) {
    let buttonText = done ? "archiwizuj" : "usuń";
    let buttonId = done ? "arc" : "del";
    return `<td><input id="or_sts_${id}" type="checkbox" disabled ${done ? 'checked' : ''}/></td>
                            <td>${client.name}${siteAddress === "" ? "" : ", " + siteAddress}</td>
                            <td>${id}</td>
                            <td>${date}</td>
                            <td>${time.toString().substring(0, 5)}</td>
                            <td>${amount}${pump ? " + P" : ""}</td>
                            <td><button id="or_mng_${id}" ${done ? 'disabled' : ''}>Zaplanuj</button></td>
                            <td><button id="or_edt_${id}" ${done ? 'disabled' : ''}>Edytuj</button></td>
                            <td><button id="or_${buttonId}_${id}">${buttonText}</button></td>`;
}

async function editOrderForm(id) {
    const data = await getEntity(ordersUrl, idPreparer(id));
    await orderModalFill(data);
}

async function orderModalFill({date, time, amount, concreteClass, siteAddress, pump, description, client}) {
    document.querySelector('#client_selection').value = client.id;
    document.querySelector('#date').value = date;
    document.querySelector('#time').value = time;
    document.querySelector('#address').value = siteAddress;
    document.querySelector('#amount').value = amount;
    document.querySelector('#conc_class').value = concreteClass;
    document.querySelector('#if_pump').checked = pump;
    document.querySelector('#description').value = description;
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
    const response = await updateServerEntity(ordersUrl, id, data);
    if (response !== null) {
        await closingModal();
        patchOrderRow(response);
    }
}

function patchOrderRow(order) {
    let tableRow = document.getElementById('or_row_' + order.id);
    tableRow.innerHTML = orderTableRowCreating(order);
}

async function archiveOrder(archiveUrl, id){
    await postToArchive(archiveUrl, JSON.stringify({"orderId": id}))
}

// MANAGEMENTS

async function managementsLoading(url) {
    const response = await getEntityList(url);
    await createPlan(response);
    tabButtonDisabling(management_button_tab);
}

async function managementWindowOpen(id) {
    await createVehicleList();
    temporaryOrder = await getEntity(ordersUrl, id);
    document.querySelector('#part_time').value = temporaryOrder.time.toString().substring(0,5);
    await orderBatchLoading(id);
    leftAmountCalc();
    orderAmount = parseFloat(temporaryOrder.amount);
    await managementFooterModalFill(temporaryOrder);
}

async function orderBatchLoading(id) {
    let response = await getEntityList(managementsUrl + "/search/" + id);
    if (response !== null) {
        partId = 0;
        for (const resp of Object.values(response)) {
            addPartToTemporaryBatchList(resp.id, resp.vehicle.id, resp.vehicle.type, resp.vehicle.regNo,
                resp.vehicle.name, resp.amount, resp.time, false);
            let vehicleTypeAndName = vehicleTypeParser(resp.vehicle.type) + " " + resp.vehicle.regNo + " " + resp.vehicle.name;
            await createPart(vehicleTypeAndName, resp.amount, resp.time);
        }
    }
}

async function orderBatchStatusHandler(id) {
    return await patchServerEntity(managementsUrl, id);
}

async function managementFooterModalFill({pump, description}) {
    document.querySelector('#amount_left').innerHTML = (orderAmount - vehicleAmountSum).toString();
    document.querySelector('#pump_nec').innerHTML = (pump === false ? "nie" : "tak");
    document.querySelector('#ad_descr').innerHTML = description;
}

function leftAmountCalc() {
    vehicleAmountSum = 0;
    for (let i = 0; i < temporaryBatchList.length; i++) {
        vehicleAmountSum += parseFloat(temporaryBatchList[i].amount);
    }
}

async function partFormReader() {
    let vehicle = document.querySelector('#vehicle_selection');
    let amount = document.querySelector('#part_amount').value;
    let time = document.querySelector('#part_time').value;

    amount = parseFloat(amount);
    amount = amount < 0 ? 0 : amount;
    let vehicleEntity = await getEntity(vehiclesUrl, parseInt(vehicle.value));
    let vehCapacity = parseFloat(vehicleEntity.capacity);
    amount = amount <= vehCapacity ? amount : vehCapacity;

    let vehicleTextValue = vehicle.options[vehicle.selectedIndex].text;
    amount = (vehicleAmountSum + amount >= orderAmount) ? orderAmount - vehicleAmountSum : amount;
    amount = vehicleTextValue.includes("Pompa") ? 0 : amount;

    await addPartToTemporaryBatchList(0, vehicleEntity.id, vehicleEntity.type, vehicleEntity.regNo,
        vehicleEntity.name, amount, time, false);
    createPart(vehicleTextValue, amount, time);
    footerDescriptionUpdate();
}

function createPart(vehicleTypeAndName, amount, time) {
    const table_container = document.getElementById('table_container');
    let table = document.getElementById(partTableName);
    const tableHeader = document.createElement('tr');
    const tableRow = document.createElement('tr');
    tableRow.setAttribute('id', 'pt_row_' + partId);
    if (table === null) {
        table = document.createElement('table');
        table.setAttribute('id', partTableName);
        table.setAttribute('class', 'part_table');
        tableHeader.innerHTML = partTableHeaderRowCreating();
        tableHeader.setAttribute('class', 'tr_header');
        table.appendChild(tableHeader);
        table_container.appendChild(table);
    }
    tableRow.innerHTML = partTableRowCreating(vehicleTypeAndName, amount, time);
    table.appendChild(tableRow);
}

function addPartToTemporaryBatchList(orderBatchId, vehicleId, vehType, vehRegNo, vehName, amount, time, toDelete) {
    vehicleAmountSum += amount;
    let part = {
        orderBatchId: orderBatchId,
        vehicleId: vehicleId,
        vehicleType: vehType,
        vehicleRegNo: vehRegNo,
        vehicleName: vehName,
        amount: amount,
        time: time,
        toDelete: toDelete
    };
    partId += 1;
    temporaryBatchList.push(part);
}

function partTableHeaderRowCreating() {
    return `        <th>pojazd</th>
                    <th>ilość</th>
                    <th>godz. załadunku</th>
                    <th>edycja</th>`
}

function partTableRowCreating(vehicleData, amount, time) {
    return `    <td>${vehicleData}</td>
                <td class="table_amounts">${amount === 0 ? "-" : amount}</td>
                <td>${time.toString().substring(0, 5)}</td>
                <td><button id="pt_del_${partId}">Usuń</button></td>`;
}

function footerDescriptionUpdate() {
    let left = orderAmount - vehicleAmountSum;
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
        listElement.text = vehicleTypeParser(vehicle.type) + ", " + vehicle.regNo + ", " + vehicle.name;
        selectTag.appendChild(listElement);
    }
    vehicleSelectedList.appendChild(selectTag);
}

function managementTableFormReader() {
    let data = [];
    for (const part of temporaryBatchList) {
        data.push(JSON.stringify({
            "batchId": part.orderBatchId, "amount": part.amount, "time": part.time, "toDelete": part.toDelete,
            "order": {"id": temporaryOrder.id},
            "vehicle": {"id": part.vehicleId}
        }));
    }
    return "[" + data + "]";
}

async function addingPlanHandler() {
    const data = managementTableFormReader();
    if (data !== "[]") {
        await postToServer(managementsUrl, data);
    }
    await closingModal();
}

async function createPlan(planTable) {
    const table = document.getElementById(managementsTableName);
    if (table === null) {
        await tableCreating(managementsTableName);
    }
    for (const row of Object.values(planTable)) {
        const tableRow = document.createElement('tr');
        tableRow.setAttribute('id', "mg_row_" + row.id);
        tableRow.innerHTML = await managementTableRowCreating(row);
        table.appendChild(tableRow);
    }
}

async function managementTableRowCreating({id, amount, time, done, order, vehicle}) {
    return `<td><input id="pt_sts_${id}" type="checkbox" ${done ? 'checked' : ''}/></td>
            <td id="mg__td_${id}">${order.id}</td>
            <td>${vehicleTypeParser(vehicle.type)}, ${vehicle.regNo}, ${vehicle.name}</td>
            <td>${order.client.name}</td>
            <td>${order.siteAddress}</td>
            <td>${amount !== 0.0 ? amount : "-"}${vehicle.type === 3 ? "/P" : ""}</td>
            <td>${order.date}, ${time.toString().substring(0, 5)}</td>
            <td><button id="mg_del_${id}" ${done ? 'disabled' : ''}>Usuń</button></td>
            <td><button id="mg_prt_${id}" ${done ? 'disabled' : ''}>WZ</button></td>`;
}

async function printHandler(id){
    let response = await getEntity(managementsUrl, id);
    await print(response);
}

// CLIENTS

async function clientsLoading(url) {
    const response = await getEntityList(url);
    for (const resp of response) {
        createClient(resp);
    }
    tabButtonDisabling(clients_button_tab);
}

function clientFormReader() {
    let client_name = document.querySelector("#client_name");
    let street = document.querySelector('#street');
    let post_code = document.querySelector("#post_code");
    let city = document.querySelector("#city");
    let nip = document.querySelector("#nip");

    return JSON.stringify({
        "id": clientAddModalActive ? "" : actualId, "name": client_name.value,
        "streetAndNo": street.value, "postCode": post_code.value, "city": city.value, "nip": nip.value
    });
}

async function editClientForm(id) {
    const data = await getEntity(clientsUrl, idPreparer(id));
    await clientModalFill(data);
}

function clientModalFill({name, streetAndNo, postCode, city, nip}) {
    document.querySelector("#client_name").value = name;
    document.querySelector('#street').value = streetAndNo;
    document.querySelector("#post_code").value = postCode;
    document.querySelector("#city").value = city;
    document.querySelector("#nip").value = nip;
}

async function saveClient() {
    const data = clientFormReader();
    const response = await postToServer(clientsUrl, data);
    if (response !== null) {
        await closingModal();
        createClient(response);
    }
}

async function patchClient(id) {
    const data = clientFormReader();
    const response = await updateServerEntity(clientsUrl, id, data);
    if (response !== null) {
        await closingModal();
        patchClientRow(response);
    }
}

function createClient(client) {
    const table = document.getElementById(clientsTableName);
    if (table === null){
        return;
    }
    const tableRow = document.createElement('tr');
    tableRow.setAttribute('id', "cl_row_" + client.id);
    tableRow.innerHTML = clientTableRowCreating(client);
    table.appendChild(tableRow);
}

function patchClientRow(client) {
    let tableRow = document.getElementById('cl_row_' + client.id);
    tableRow.innerHTML = clientTableRowCreating(client);
}

function clientTableRowCreating({id, name, streetAndNo, postCode, city, nip}) {
    return `
                            <td>${name}</td>
                            <td>${streetAndNo}, ${postCode} ${city}</td>
                            <td>${nip.toString()
        .substring(0, 3)}-${nip.toString()
        .substring(3, 6)}-${nip.toString()
        .substring(6, 8)}-${nip.toString()
        .substring(8)}</td>
                            <td><button id="cl_edt_${id}">Edytuj</button></td>
                            <td><button id="cl_del_${id}">Usuń</button></td>`;
}

// VEHICLES

async function vehiclesLoading(url) {
    const response = await getEntityList(url);
    for (const resp of response) {
        createVehicle(resp);
    }
    tabButtonDisabling(vehicles_button_tab);
}

function vehicleFormReader() {
    let vehicle_name = document.querySelector("#vehicle_name");
    let vehicle_type = document.querySelector('#vehicle_type');
    let capacity = document.querySelector("#capacity");
    let pump_length = document.querySelector("#pump_length");
    let reg_no = document.querySelector("#reg_no");

    return JSON.stringify({
        "id": vehicleAddModalActive ? "" : actualId, "name": vehicle_name.value, "type": vehicle_type.value,
        "capacity": capacity.value, "pumpLength": pump_length.value, "regNo": reg_no.value
    });
}

async function editVehicleForm(id) {
    const data = await getEntity(vehiclesUrl, idPreparer(id));
    await vehicleModalFill(data);
}

function vehicleModalFill({name, type, capacity, pumpLength, regNo}) {
    document.querySelector("#vehicle_name").value = name;
    document.querySelector('#vehicle_type').value = type;
    document.querySelector("#capacity").value = capacity;
    document.querySelector("#pump_length").value = pumpLength;
    document.querySelector("#reg_no").value = regNo;
}

async function saveVehicle() {
    const data = vehicleFormReader();
    const response = await postToServer(vehiclesUrl, data);
    if (response !== null){
        await closingModal();
        createVehicle(response);
    }
}

async function patchVehicle(id) {
    const data = vehicleFormReader();
    const response = await updateServerEntity(vehiclesUrl, id, data);
    if (response !== null) {
        await closingModal();
        patchVehicleRow(response);
    }
}

function createVehicle(vehicle) {
    const table = document.getElementById(vehiclesTableName);
    if (table === null){
        return;
    }
    const tableRow = document.createElement('tr');
    tableRow.setAttribute('id', "vh_row_" + vehicle.id);
    tableRow.innerHTML = vehicleTableRowCreating(vehicle);
    table.appendChild(tableRow);
}

function patchVehicleRow(vehicle) {
    let tableRow = document.getElementById('vh_row_' + vehicle.id);
    tableRow.innerHTML = vehicleTableRowCreating(vehicle);
}

function vehicleTableRowCreating({id, name, type, capacity, pumpLength, regNo}) {
    return `    <td>${name}</td>
                <td>${vehicleTypeParser(type)}</td>
                <td>${type === "PUMP" ? "-" : capacity}</td>
                <td>${type === "MIXER" ? "-" : pumpLength}</td>
                <td>${regNo}</td>
                <td><button id="vh_edt_${id}">Edytuj</button></td>
                <td><button id="vh_del_${id}">Usuń</button></td>`;
}

function vehicleTypeParser(type){
    switch (type){
        case "MIXER": return "gruszka";
        case "MIXER_PUMP": return "pompo-gruszka";
        case "PUMP": return "pompa";
        default: "-";
    }
}

// ARCHIVE

async function archiveLoading(url) {
    const response = await getEntityList(url);
    for (const resp of response) {
        createArchive(resp);
    }
    tabButtonDisabling(archive_button_tab);
}

function createArchive(archiveRow) {
    const table = document.getElementById(archiveTableName);
    if (table === null){
        return;
    }
    const tableRow = document.createElement('tr');
    tableRow.innerHTML = archiveTableRowCreating(archiveRow);
    table.appendChild(tableRow);
}

function archiveTableRowCreating(archiveRow) {
    return `    <td>${archiveRow.dnNo}</td>
                <td>${archiveRow.date}</td>
                <td>${archiveRow.time.toString().substring(0, 5)}</td>
                <td>${archiveRow.siteAddress}</td>
                <td>${archiveRow.clientName + ", " + archiveRow.clientAddress + ", " + archiveRow.clientPostCode +
                " " + archiveRow.clientCity + ", "  + archiveRow.clientNip}</td>
                <td>${archiveRow.vehicleType + " " + archiveRow.vehicleName + " " + archiveRow.vehicleRegNo}</td>
                <td>${archiveRow.concreteClass}</td>
                <td>${archiveRow.amount === 0.0 ? "-" : archiveRow.amount}</td>`;
}

// SERVER OPERATIONS

async function postToServer(url, data) {
    return await fetch(url, {
        method: 'POST',
        body: data,
        headers: {'Content-Type': 'application/json'}
    })
        .then(resp => {
            if (resp.status < 200 && resp.status > 299) {
                return null;
            }
            return resp.json();
        });
}

async function postToArchive(archiveUrl, data){
    await fetch(archiveUrl, {
        method: 'POST',
        body: data,
        headers: {'Content-Type': 'application/json'}
    });
}

async function updateServerEntity(url, id, data) {
    return await fetch(url + '/' + id, {
        method: 'PUT',
        body: data,
        headers: {'Content-Type': 'application/json'}
    })
        .then(resp => {
            if (resp.status < 200 && resp.status > 299) {
                return null;
            }
            return resp.json();
        });
}

async function patchServerEntity(url, id) {
    let response = await fetch(url + '/' + id, {
        method: 'PATCH',
        headers: {'Content-Type': 'application/json'}
    });
    return response.status < 200 && response.status > 299;
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
    let response = await fetch(url + '/' + id, {method: 'DELETE'});
    return response.status >= 200 && response.status < 299;
}

// GENERAL HTML ELEMENTS

async function tableCreating(tableName) {
    await tableRemoving();
    const table = document.createElement('table');
    table.setAttribute('id', tableName);
    tableContainer.appendChild(table);
    let headerRow = document.createElement('tr');
    headerRow.setAttribute('class', 'tr_header');
    headerRow.innerHTML = tableHeaderRowCreating(tableName);
    table.appendChild(headerRow);
}

function tableHeaderRowCreating(tableName) {
    switch (tableName) {
        case ordersTableName: {
            return `<th>status</th>
            <th>klient i adres budowy</th>
            <th>id zamówienia</th>
            <th>data</th>
            <th>godzina</th>
            <th>ilość</th>
            <th colSpan="3">Edycja</th>`
        }
        case managementsTableName: {
            return `<th>status</th>
            <th>nr zamówienia</th>
            <th>typ i nazwa pojazdu</th>
            <th>zleceniodawca</th>
            <th>adres budowy</th>
            <th>ilość</th>
            <th>wyjazd</th>
            <th colSpan="2">Edycja</th>`
        }
        case clientsTableName: {
            return `<th>Nazwa</th>
            <th>Adres</th>
            <th>NIP</th>
            <th colSpan="2">Edycja</th>`
        }
        case vehiclesTableName: {
            return `<th>nazwa</th>
            <th>typ</th>
            <th>pojemność</th>
            <th>długość pompy</th>
            <th>nr rejestr.</th>
            <th colspan="2">Edycja</th>`
        }
        case archiveTableName: {
            return `<th>nr wz</th>
            <th>data</th>
            <th>godzina</th>
            <th>adres budowy</th>
            <th>klient</th>
            <th>pojazd</th>
            <th>klasa betonu</th>
            <th>ilość</th>`
        }
    }
}

async function sortOptionsCreating(tableName) {
    await sortOptionsRemoving();
    sortButton.style.display = "block";
    const selectList = document.createElement('select');
    selectList.setAttribute('id', "sort_" + tableName);
    footerSortOptionsContainer.appendChild(selectList);
    selectList.innerHTML = createSortOptionsList(tableName);
}

function createSortOptionsList(tableName) {
    switch (tableName) {
        case ordersTableName: {
            return `<option value="date,asc">data rosnąco</option>
                        <option value="date,desc">data malejąco</option>
                        <option value="client.name,asc">klient rosnąco</option>
                        <option value="client.name,desc">klient malejąco</option>
                        <option value="id,asc">nr zam. rosnąco</option>
                        <option value="id,desc">nr zam. malejąco</option>
                        <option value="amount,asc">ilość rosnąco</option>
                        <option value="amount,desc">ilość malejąco</option>`
        }
        case managementsTableName: {
            return `<option value="order.date,asc&sort=time,asc">czas rosnąco</option>
                        <option value="order.date,desc&sort=time,desc">czas malejąco</option>`
        }
        case clientsTableName: {
            return `<option value="name,asc">nazwa rosnąco</option>
                        <option value="name,desc">nazwa malejąco</option>
                        <option value="nip,asc">NIP rosnąco</option>
                        <option value="nip,desc">NIP malejąco</option>`
        }
        case vehiclesTableName: {
            return `<option value="name,asc">nazwa rosnąco</option>
                        <option value="name,desc">nazwa malejąco</option>
                        <option value="regNo,asc">nr rej. rosnąco</option>
                        <option value="regNo,desc">nr rej. malejąco</option>
                        <option value="type,asc">typ rosnąco</option>
                        <option value="type,desc">typ malejąco</option>`
        }
        case archiveTableName: {
            return `<option value="dnNo,asc">nr wz rosnąco</option>
                        <option value="dnNo,desc">nr wz malejąco</option>
                        <option value="date,asc&sort=time,asc">data rosnąco</option>
                        <option value="date,desc&sort=time,desc">data malejąco</option>`
        }
    }
}

function sortOptionsRemoving() {
    let sortList = document.getElementById(sortContainer);
    let firstElementChild = sortList.firstElementChild;
    if (firstElementChild !== null) {
        firstElementChild.remove();
    }
}

// PRINTING

function print({dn_no, date, time, site_address, client_and_address,
                   vehicle_name, vehicle_reg, vehicle_type, amount, c_class}) {
    const dnTemplate = document.getElementById("dn_print");
    const iframeWindow = dnTemplate.contentWindow;
    const iframeDoc = dnTemplate.contentDocument;

    iframeDoc.getElementById("dn_title").textContent = dn_no;
    iframeDoc.getElementById("dn_no").textContent = dn_no;
    iframeDoc.getElementById("dn_date").textContent = date;
    iframeDoc.getElementById("dn_address").textContent = site_address;
    iframeDoc.getElementById("dn_client").textContent = client_and_address;
    iframeDoc.getElementById("dn_veh_name").textContent = vehicle_name;
    iframeDoc.getElementById("dn_veh_reg").textContent = vehicle_reg;
    iframeDoc.getElementById("dn_amount").textContent = amount;
    iframeDoc.getElementById("dn_class").textContent = c_class;
    iframeDoc.getElementById("dn_time").textContent = time;
    iframeDoc.getElementById("dn_extras").textContent =
        vehicle_type === "MIXER_PUMP" || vehicle_type === "PUMP" ? "Pompa" : "";

    iframeWindow.focus();
    iframeWindow.print();
}