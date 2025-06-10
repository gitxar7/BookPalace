/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

async function loadCartItems() {

    const response = await fetch("LoadCartItems");

    if (response.ok) {
        const json = await response.json();
        let cartItemContainer = document.getElementById("cart-item-container");
        let cartItemRow = document.getElementById("cart-item");
        let cartSummary = document.getElementById("cart-summary");
        let emptyCartBtn = document.getElementById("remove-all-cart");

        cartItemContainer.innerHTML = "";

//        console.log(json);

        if (json.length === 0) {
            cartSummary.classList = "d-none";
            emptyCartBtn.classList = "d-none";
            document.getElementById("empty-cart").innerHTML = emptyCart();

        } else {
            cartSummary.classList = "d-block col-12 col-lg-3";
            emptyCartBtn.classList = "col-12 d-flex justify-content-end mb-1";
            let totalQty = 0;
            let total = 0;
            let totalx = 0;
            let service = 0;

            json.forEach(item => {
//                console.log(item.product.title);

                let itemSubtotal = item.product.price * item.qty;
                total += itemSubtotal;
                totalx += service + total;

                totalQty += item.qty;
                const title = item.product.title;
                const titleShort = title.length > 20 ? title.substring(0, 20) + "..." : title;
                const price = item.product.price;
                const price2 = ((5 / 100) * price) + price;

                let cartItemRowClone = cartItemRow.cloneNode(true);
                cartItemRowClone.querySelector("#image-cover").href = "single-product-view.html?pid=" + item.product.id;
                cartItemRowClone.querySelector("#image").style.backgroundImage = "url('ProductCoverImages/" + item.product.id + ".png')";
//                cartItemRowClone.querySelector("#image").data-bs-content = item.product.description;
                cartItemRowClone.querySelector("#name").innerHTML = titleShort;
                cartItemRowClone.querySelector("#seller-name").innerHTML = item.product.user.name;
                cartItemRowClone.querySelector("#author").innerHTML = item.product.author;
                cartItemRowClone.querySelector("#isbn").innerHTML = item.product.isbn;
                cartItemRowClone.querySelector("#genre-age").innerHTML = item.product.genre.name + " | " + item.product.age_rating.name;
                cartItemRowClone.querySelector("#price").innerHTML = "Rs." + price + ".00";
                cartItemRowClone.querySelector("#rq").innerHTML = "Requested Total (" + item.qty + " items):";
                cartItemRowClone.querySelector("#rq-total").innerHTML = "Rs." + price2 * item.qty + ".00";
                cartItemRowClone.querySelector("#remove-all-btn").addEventListener("click", (e) => {
                    removeCartItem(item.id, "one");
                    e.preventDefault();

                });

                cartItemRowClone.querySelector("#remove-btn").addEventListener("click", (e) => {
                    updateCart(item.id, "remove");
                    e.preventDefault();

                });

                cartItemRowClone.querySelector("#add-btn").addEventListener("click", (e) => {
//                    console.log(item.id);
                    updateCart(item.id, "add");
                    e.preventDefault();

                });
                cartItemContainer.appendChild(cartItemRowClone);

            });

            service += ((5 / 100) * total);
            document.getElementById("items").innerHTML = "Items(" + totalQty + ")";
            document.getElementById("items-price").innerHTML = "Rs." + total + ".00";
            document.getElementById("sc-price").innerHTML = "Rs." + service + ".00";
            document.getElementById("total-price").innerHTML = "Rs." + (total + service) + ".00";

        }

    } else {

    }

}


function emptyCart() {
    return `<div class="row">
    <div class="col-12 emptyCart"></div>
    <div class="col-12 text-center mb-2">
    <label class="form-label fs-1 fw-bold">
    Oops, cart is empty.....
    </label>
    </div>
    <div class="offset-lg-4 col-12 col-lg-4 mb-4 d-grid">
    <a href="index.html" class="btn btn-outline-success fs-3 fw-bold">
    Start Shopping?
    </a>
    </div>
    </div>`;
}


async function removeCartItem(cid, del) {
//    console.log("888");
    const response = await fetch("RemoveCartItems?cid=" + cid + "&del=" + del);

    if (response.ok) {
        const json = await response.json();
        if (json.success) {
            alert(json.content);
            loadCartItems();
        } else {
            alert(json.content);
        }
    } else {
    }
}

async function updateCart(cid, act) {

    const response = await fetch("UpdateCart?cid=" + cid + "&act=" + act);

    if (response.ok) {
        const json = await response.json();
        if (json.success) {
            alert(json.content);
            loadCartItems();
        } else {
            alert(json.content);
        }
    } else {

    }

}
