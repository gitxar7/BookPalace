/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

async function loadMyProfile() {
//    console.log("loadMyProfile");
    const response = await fetch("LoadProfile");

    if (response.ok) {
        const json = await response.json();
        if (json.success) {
            const user = json.user;
            const address = json.address;
            const cityList = json.cityList;

            document.getElementById("my-name").innerHTML = user.name;
            document.getElementById("my-email").innerHTML = user.email;

            document.getElementById("name").value = user.name;
            document.getElementById("email").value = user.email;
            document.getElementById("password").value = "123456789";
            document.getElementById("date").value = user.datetime;
            document.getElementById("mobile").value = address.mobile;
            document.getElementById("city").value = address.city.name;
            document.getElementById("postal").value = address.postel;
            document.getElementById("line1").value = address.line1;
            document.getElementById("line2").value = address.line2 === undefined ? " " : address.line2;

            const selectTag = document.getElementById("city-select");
            cityList.forEach(item => {
                let optionTag = document.createElement("option");
                optionTag.classList = "bg-black bg-opacity-50";
                optionTag.value = item.id;
                optionTag.innerHTML = item.name;
                selectTag.appendChild(optionTag);
            });

        } else {

        }
    } else {

    }
}

async function addAddress() {
    const dto = {
        line1: document.getElementById("add-line1").value,
        line2: document.getElementById("add-line2").value,
        postal: document.getElementById("add-postal").value,
        city: document.getElementById("city-select").value
    };

    const response = await fetch(
            "AddAddress",
            {
                method: "POST",
                body: JSON.stringify(dto),
                headers: {
                    "Content-Type": "application/json"
                }
            }
    );

    if (response.ok) {
        const json = await response.json();

        if (json.success) {
            alert(json.content);
            location.reload(true);
        } else {
            document.getElementById("error-message").innerHTML = json.content;
        }
    } else {

    }
}

let productList;
async function loadMyProducts() {
//    console.log("loadMyProfile");
    const response = await fetch("LoadProfile");

    if (response.ok) {
        const json = await response.json();
        if (json.success) {
            const user = json.user;
            productList = json.productList;

            document.getElementById("name").innerHTML = user.name;
            document.getElementById("email").innerHTML = user.email;

//            const productHtml = document.getElementById("product-template");
//            document.getElementById("my-products").innerHTML = "";
//            console.log(productList);
            loadProductData(productList);

//            for(var i = count; i<count*2;i++){
//                console.log(productList[i]);
//            }

//            productList.forEach(item => {
//                let productCloneHtml = productHtml.cloneNode(true);
//                const title = item.title;
//                const titleShort = title.length > 30 ? title.substring(0, 30) + "..." : title;
//
//                productCloneHtml.querySelector("#name").innerHTML = titleShort;
//                productCloneHtml.querySelector("#genre-age").innerHTML = item.genre.name + " | " + item.age_rating.name;
//                productCloneHtml.querySelector("#price").innerHTML = "Rs." + item.price + ".00";
//                productCloneHtml.querySelector("#author").innerHTML = item.author;
//                productCloneHtml.querySelector("#image").src = "ProductCoverImages/" + item.id + ".png";
//
//                productCloneHtml.querySelector("#image").addEventListener("click", (e) => {
//                    window.location = "single-product-view.html?pid=" + item.id;
//                    e.preventDefault();
//                });
//
//                productCloneHtml.querySelector("#remove").addEventListener("click", (e) => {
//                    removeProduct(item.id);
//                    e.preventDefault();
//                });
//
//                document.getElementById("my-products").appendChild(productCloneHtml);
//            });

        } else {
        }
    } else {
    }
}

let page = 1;
let count = 1;

function loadProductData() {
    count = Math.ceil(productList.length / 2);
    let length = count == page ? productList.length : page * 2;
    const productHtml = document.getElementById("product-template");
    document.getElementById("my-products").innerHTML = "";
    const prevButton = document.getElementById("btn-prev");
    const nxtButton = document.getElementById("btn-nxt");

    document.getElementById("page-number").innerHTML = page;
    document.getElementById("page-count").innerHTML = count;

    if (count <= 1) {
        document.getElementById("page-container").innerHTML = "";
    }

    if (page == count) {
        nxtButton.classList = "d-none";
    } else {
        nxtButton.classList = "btn btn-primary rounded-circle";
    }

    if (page == 1) {
        prevButton.classList = "d-none";
    } else {
        prevButton.classList = "btn btn-primary rounded-circle";
    }

    for (let index = (page * 2 - 2); index < length; index++) {
        const item = productList[index];
        let productCloneHtml = productHtml.cloneNode(true);
        const title = item.title;
        const titleShort = title.length > 30 ? title.substring(0, 30) + "..." : title;
        productCloneHtml.querySelector("#name").innerHTML = titleShort;
        productCloneHtml.querySelector("#genre-age").innerHTML = item.genre.name + " | " + item.age_rating.name;
        productCloneHtml.querySelector("#price").innerHTML = "Rs." + item.price + ".00";
        productCloneHtml.querySelector("#author").innerHTML = item.author;
        productCloneHtml.querySelector("#image").src = "ProductCoverImages/" + item.id + ".png";
        productCloneHtml.querySelector("#image").addEventListener("click", (e) => {
            window.location = "single-product-view.html?pid=" + item.id;
            e.preventDefault();
        });
        productCloneHtml.querySelector("#remove").addEventListener("click", (e) => {
            removeProduct(item.id);
            e.preventDefault();
        });
        document.getElementById("my-products").appendChild(productCloneHtml);
    }
}

function reLoadProducts(increase) {
    if (increase) {
        if (page < count) {
            page++;
            loadProductData();
        }
    } else {
        if (page > 1) {
            page--;
            loadProductData();
        }
    }
}

async function removeProduct(pid) {
    if (confirm("Are you sure you want to DELETE this product?")) {
        const response = await fetch("RemoveMyProducts?pid=" + pid);

        if (response.ok) {
            const json = await response.json();
            if (json.success) {
                alert(json.content);
                location.reload(true);
            } else {
            }
        } else {
        }
    }
}



