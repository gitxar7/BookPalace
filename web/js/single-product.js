/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


async function loadProduct() {

    const parameters = new URLSearchParams(window.location.search);

    if (parameters.has("pid")) {
        const pid = parameters.get("pid");
        const response = await fetch("LoadSingleProduct?pid=" + pid);

        if (response.ok) {
            const json = await response.json();

            const id = json.product.id;
            document.getElementById("image").style.backgroundImage = "url('ProductCoverImages/" + pid + ".png')";
//            document.getElementById("image").src = "ProductCoverImages/3.png";
            const description = json.product.description;
            const descShort = description.length > 300 ? description.substring(0, 300) + "..." : description;


            document.getElementById("title").innerHTML = json.product.title;
            document.getElementById("author").innerHTML = "By. " + json.product.author;
            document.getElementById("description").innerHTML = descShort;
            document.getElementById("price").innerHTML = "Rs." + json.product.price + ".00";
            document.getElementById("genre").innerHTML = json.product.genre.name;
            document.getElementById("age").innerHTML = json.product.age_rating.name;
            document.getElementById("isbn").innerHTML = json.product.isbn;
            document.getElementById("condition").innerHTML = json.product.product_condition.name;
            document.getElementById("seller").innerHTML = json.product.user.name;
            document.getElementById("qty").innerHTML = json.product.quantity;
            document.getElementById("date").innerHTML = json.product.datetime;
            document.getElementById("add-to-cart").addEventListener("click", (e) => {
                let cart_qty = document.getElementById("cart-qty");
                cart_qty.max = json.product.quantity;
                addToCart(json.product.id, cart_qty.value);
                e.preventDefault();
//                console.log(cart_qty);
            });

//            document.getElementById("payHere-payment").addEventListener("click", (e) => {
//                checkout(json.product.id);
//                e.preventDefault();
//            });

            let productHtml = document.getElementById("similar-product");
            document.getElementById("similar-product-main").innerHTML = "";

            json.productList.forEach(item => {
//                console.log(item.title);

                let productCloneHtml = productHtml.cloneNode(true);
                const title = item.title;
                const titleShort = title.length > 10 ? title.substring(0, 10) + "..." : title;

                productCloneHtml.querySelector("#sp-link").href = "single-product-view.html?pid=" + item.id;
                productCloneHtml.querySelector("#sp-image").style.backgroundImage = "url('ProductCoverImages/" + item.id + ".png')";

                productCloneHtml.querySelector("#sp-name").innerHTML = titleShort;
                productCloneHtml.querySelector("#sp-btn").addEventListener("click", (e) => {
                    addToCart(item.id, 1);
                    e.preventDefault();
                });

                document.getElementById("similar-product-main").appendChild(productCloneHtml);
            });

        } else {
            window.location = "index.html";
        }
    } else {
        window.location = "index.html";
    }
}


async function addToCart(pid, qty) {

    const response = await fetch("AddToCart?pid=" + pid + "&qty=" + qty);

    if (response.ok) {
        const json = await response.json();
        if (json.success) {
            alert(json.content);
        } else {
            alert(json.content);
        }
    } else {

    }

}