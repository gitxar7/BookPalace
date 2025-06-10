/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

async function loadHomePage() {
    loadFeatures(true);
    loadHomeItems();
}


async function loadHomeItems() {
    console.log("loadHome");
    const response = await fetch("LoadHomePage");
    if (response.ok) {
        const json = await response.json();

        const genreList = json.genreList;
        const ageList = json.ageList;
        const productList = json.productList;

        let genreItem = document.getElementById("genre-item");
        document.getElementById("genre-container").innerHTML = "";
//        console.log(json.genreList);
        json.genreList.forEach(item => {
            let genreItemClone = genreItem.cloneNode(true);
            genreItemClone.querySelector("#genre-image").style.backgroundImage = "url('images/genre/" + item.id + ".jpg')";
            genreItemClone.querySelector("#genre-name").innerHTML = item.name;
            genreItemClone.addEventListener("click", (e) => {
                basicSearch(false, item.id, false);
                document.getElementById("genre").value = item.id;
                e.preventDefault();
            });
            document.getElementById("genre-container").appendChild(genreItemClone);
        });

        let ageItem = document.getElementById("age-item");
        document.getElementById("age-container").innerHTML = "";
        json.ageList.forEach(item => {
//            console.log(item.name);
            let ageItemClone = ageItem.cloneNode(true);
            ageItemClone.querySelector("#age-image").style.backgroundImage = "url('images/age/" + item.id + ".png')";
            ageItemClone.querySelector("#age-name").innerHTML = item.name;
            ageItemClone.addEventListener("click", (e) => {
                basicSearch(false, false, item.id);
                e.preventDefault();
            });
            document.getElementById("age-container").appendChild(ageItemClone);
        });

        let productItem = document.getElementById("product-item");
        document.getElementById("product-container").innerHTML = "";
//        console.log(json.productList);
        json.productList.forEach(item => {
//            console.log(item.title);
            const title = item.title;
            const titleShort = title.length > 20 ? title.substring(0, 20) + "..." : title;
            let productItemClone = productItem.cloneNode(true);
            productItemClone.querySelector("#product-image").style.backgroundImage = "url('ProductCoverImages/" + item.id + ".png')";
            productItemClone.querySelector("#product-name").innerHTML = titleShort;
            productItemClone.querySelector("#product-rating-genre").innerHTML = item.genre.name + " | " + item.age_rating.name;
            productItemClone.querySelector("#product-price").innerHTML = "Rs." + item.price + ".00";
            productItemClone.querySelector("#product-link").href = "single-product-view.html?pid=" + item.id;
            productItemClone.querySelector("#product-cart-btn").addEventListener("click", (e) => {
                addToCart(item.id, 1);
                e.preventDefault();
            });
            document.getElementById("product-container").appendChild(productItemClone);
        });



    } else {
        document.getElementById("error-message").innerHTML = "Error: Please try again later!";
        document.getElementById("error-message").classList = "text-danger form-label fw-bold fs-5 bg-opacity-50 bg-black p-1 rounded-2";

    }

}



