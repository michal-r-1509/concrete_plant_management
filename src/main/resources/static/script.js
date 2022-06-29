const orderModal = document.getElementById('order_modal');
const clientModal = document.getElementById('client_modal');
const vehicleModal = document.getElementById('vehicle_modal');

const orderModalBtn = document.getElementById('order_button');
const clientModalBtn = document.getElementById('client_button');
const vehicleModalBtn = document.getElementById('vehicle_button');

const clientEditBtn = document.getElementsByClassName('clientEditBtn');
const clientDeleteBtn = document.getElementsByClassName('clientDeleteBtn');

const closeButton = document.getElementsByClassName("close");
const form = document.getElementsByClassName('data_form');

const overlay = document.getElementById('overlay');

for (let i = 0; i < closeButton.length; i++) {
    closeButton[i].onclick = function () {
        orderModal.style.display = "none";
        clientModal.style.display = "none";
        vehicleModal.style.display = "none";
        overlay.classList.remove('active');
        submitForm();
    }
}

function closingModal(){
    orderModal.style.display = "none";
    clientModal.style.display = "none";
    vehicleModal.style.display = "none";
    overlay.classList.remove('active');
    submitForm();
}

orderModalBtn.onclick = function () {
    orderModal.style.display = "block";
    overlay.classList.add('active');
}

clientModalBtn.onclick = function () {
    clientModal.style.display = "block";
    overlay.classList.add('active');
    document.getElementById('client_modal_title').innerHTML = "Dodaj klienta";
}

vehicleModalBtn.onclick = function () {
    vehicleModal.style.display = "block";
    overlay.classList.add('active');
}

for (let i = 0; i < clientEditBtn.length; i++) {
    clientEditBtn[i].onclick = function () {
        clientModal.style.display = "block";
        overlay.classList.add('active');
        document.getElementById('client_modal_title').innerHTML = "Edytuj dane klienta";
    }
}



function submitForm() {
    for (let i = 0; i < form.length; i++) {
        form[i].reset();
    }
}

function loadScript(url) {
    const head = document.getElementsByTagName('head')[0];
    const script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = url;
    head.appendChild(script);
}

loadScript("connection_script.js");


