/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function paymentTest() {
    let details = {
        "sandbox": true,
        "merchant_id": "1221775",
        "return_url": undefined, // Important
        "cancel_url": undefined, // Important
        "notify_url": "http://sample.com/notify",
        "first_name": "Peter",
        "last_name": "Parker",
        "email": "abc@gmail.com",
        "phone": "0774562154",
        "address": "Galle Road, Galle City",
        "city": "Galle",
        "country": "Sri Lanka",
        "order_id": "72454",
        "items": "Chocolate",
        "currency": "LKR",
        "amount": "1000.00",
        "hash": "B3A2A813D0EAD51087B7FE14D842FDD3"
    };
    payhere.startPayment(details);
}

async function loadData() {

    const response = await fetch("LoadCheckoutDetails");
    if (response.ok) {
        const json = await response.json();
//        console.log(json);

        if (json.success) {
            let service = ((5 / 100) * json.stotal);
            let qty = json.qty;
            let name = json.name;
            const address = json.address;
            const cartList = json.cartList;
            const cityList = json.cityList;
            let total = service + json.stotal + address.city.delivery;
            const selectTag = document.getElementById("city-select");
            cityList.forEach(item => {
                let optionTag = document.createElement("option");
                optionTag.classList = "bg-black bg-opacity-50";
                optionTag.value = item.id;
                optionTag.innerHTML = item.name;
                selectTag.appendChild(optionTag);
            });

            document.getElementById("qty").innerHTML = qty + " items";
            document.getElementById("sub-total").innerHTML = "Rs." + json.stotal + ".00";
            document.getElementById("service").innerHTML = "Rs." + service + ".00";
            document.getElementById("delivery").innerHTML = "Rs." + address.city.delivery + ".00";
            document.getElementById("total").innerHTML = "Rs." + total + ".00";

            document.getElementById("add-name").value = json.name;
            document.getElementById("add-mobile").value = address.mobile === undefined ? " " : address.mobile;
//            console.log(json.name);
            document.getElementById("add-line1").value = address.line1;
            document.getElementById("add-line2").value = address.line2 === undefined ? " " : address.line2;
            document.getElementById("add-postal").value = address.postel;
            document.getElementById("city-select").value = address.city.id;
        } else {
            window.location = "sign-in.html";
        }
    }
}


// Payment completed. It can be a successful failure.
payhere.onCompleted = function onCompleted(orderId) {
    console.log("Payment completed. OrderID:" + orderId);
    // Note: validate the payment and show success or failure page to the customer

    alert("Thank you, Payment completed!");
    window.location = "index.html";
};

// Payment window closed
payhere.onDismissed = function onDismissed() {
    // Note: Prompt user to pay again or show an error page
    console.log("Payment dismissed");
};

// Error occurred
payhere.onError = function onError(error) {
    // Note: show an error page
    console.log("Error:" + error);
};


async function checkout() {

    //get address data
    let name = document.getElementById("add-name");
    let mobile = document.getElementById("add-mobile");
    let line2 = document.getElementById("add-line2");
    let line1 = document.getElementById("add-line1");
    let postal = document.getElementById("add-postal");
    let city = document.getElementById("city-select");

    const data = {
        name: name.value,
        city: city.value,
        line1: line1.value,
        line2: line2.value,
        postal: postal.value,
        mobile: mobile.value
    };
    
    console.log(data);

    const response = await fetch("Checkout", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    });

    if (response.ok) {
        const json = await response.json();

        if (json.success) {

//            console.log(json.payhereJson);
            payhere.startPayment(json.payhereJson);
        } else {
            alert(json.message);
        }

    } else {
        alert(json.message);
    }

}

