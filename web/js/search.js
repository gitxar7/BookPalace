/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function basicSearch(isAdavanced, g_id, a_id) {
    let resultContainer = document.getElementById("basic-search-result");
    resultContainer.innerHTML = resultElement();
    search(false, g_id, a_id);
}

function advancedSearch() {
    let resultContainer = document.getElementById("advanced-search-result");
    resultContainer.innerHTML = resultElement();
    search(true, false, false);
}


let page = 1;
let count = 1;
let firstResult = 0;

async function search(isAdavanced, g_id, a_id) {

    let genre = document.getElementById("genre").value;
    let age = isAdavanced ? document.getElementById("age-rating").value : "0";
    let length = isAdavanced ? 20 : 30;
    let maxResults = isAdavanced;

    if (g_id) {
        genre = g_id;
    }
    if (a_id) {
        age = a_id;
    }

    const dto = {
        text: document.getElementById("search_txt").value,
        genre: genre,
        age: age,
        condition: isAdavanced ? document.getElementById("condition").value : "0",
        from: isAdavanced ? document.getElementById("price-from").value : "0",
        to: isAdavanced ? document.getElementById("price-to").value : "0",
        sort: isAdavanced ? document.getElementById("sort-option").value : "0",
        first_result: firstResult,
        max_result: maxResults
    };

//    console.log(dto);

    const response = await fetch(
            "Search", {
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
            const productList = json.productList;
            count = Math.ceil(json.allProductCount / 2);
//            let itemLength = count === page ? productList.length : page * 2;
            const prevButton = document.getElementById("btn-prev");
            const nxtButton = document.getElementById("btn-nxt");


            if (isAdavanced) {
                document.getElementById("page-number").innerHTML = page;
                document.getElementById("page-count").innerHTML = count;

                if (count <= 1) {
//                document.getElementById("page-container").innerHTML = "";
                    document.getElementById("page-container").classList = "d-none";
                } else {
                    document.getElementById("page-container").classList = "col-12 mb-3";
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
            }



            let productItem = document.getElementById("bs-item");
            document.getElementById("bs-container").innerHTML = "";
            json.productList.forEach(item => {
                const title = item.title;
                const titleShort = title.length > length ? title.substring(0, length) + "..." : title;
                let productItemClone = productItem.cloneNode(true);
                productItemClone.querySelector("#bs-item-image").style.backgroundImage = "url('ProductCoverImages/" + item.id + ".png')";
                productItemClone.querySelector("#bs-item-name").innerHTML = titleShort;
//                productItemClone.querySelector("#bs-item-name").ariaPlaceholder = title;
                productItemClone.querySelector("#bs-item-price").innerHTML = "Rs." + item.price + ".00";
                productItemClone.querySelector("#bs-item-buy").href = "single-product-view.html?pid=" + item.id;
                productItemClone.querySelector("#bs-item-cart").addEventListener("click", (e) => {
                    addToCart(item.id, 1);
                    e.preventDefault();
                });
                document.getElementById("bs-container").appendChild(productItemClone);
            });
        } else {

        }
    } else {
        document.getElementById("error-message").innerHTML = "Please try again later!";
    }
}

function reLoadProducts(increase) {
    if (increase) {
        if (page < count) {
            page++;
            firstResult += 2;
            search(true, false, false);
        }
    } else {
        if (page > 1) {
            page--;
            firstResult -= 2;
            search(true, false, false);
        }
    }
}


function resultElement() {
//    style="min-height: 60vh;"
    return `<div class="row" id="bs-container">
    <div class="offset-lg-1 col-12 col-lg-10 text-center">
        <div class="row">


            <div class="col-12 col-lg-4 p-2" id="bs-item">
                <div class="card col-12 bg-body bg-opacity-50 border-0 rounded-2 hvrOn">
                    <div class="row">

                        <div class="col-md-3">
                            <!-- <img src="<?php echo $selected_data["img"]; ?>" class="img-fluid rounded-start" alt="..."> -->
                            <div id="bs-item-image" class="cover-image col-12 mx-auto hgh"
                                style="background-image: url('ProductCoverImages/3.png')"></div>
                        </div>
                        <div class="col-md-9">
                            <div class="card-body">

                                <span id="bs-item-name" class="card-title fs-5 fw-bolder text-center text-black">name</span>
                                                                <br>
                                <span id="bs-item-price" class="card-text text-success fw-bold fs">Rs. 1000 .00</span>

                                <div class="row">
                                    <div class="col-12">

                                        <div class="row g-1">
                                            <div class="col-12 col-lg-6 d-grid">
                                                <a href='' id="bs-item-buy" class="btn btn-success fs">Buy Now</a>
                                            </div>
                                            <div class="col-12 col-lg-6 d-grid">
                                                <a href="#" id="bs-item-cart" class="btn btn-danger fs" onclick="">Add
                                                    Cart</a>
                                            </div>
                                        </div>

                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <!--  -->
    <div class="offset-2 offset-lg-3 col-8 col-lg-6 text-center mb-3">
       <!-- <nav aria-label="Page navigation example">
            <ul class="pagination pagination-lg justify-content-center">
                <li class="page-item">
                    <a class="page-link" onclick="basicSearch();" aria-label="Previous">
                        <span aria-hidden="true" class="text-primary">&laquo;</span>
                    </a>
                </li>
                <li class="page-item active">
                    <a class="page-link" onclick="basicSearch();">1</a>
                </li>

                <li class="page-item">
                    <a class="page-link" onclick="basicSearch();">2</a>
                </li>


                <li class="page-item">
                    <a class="page-link" onclick="basicSearch();" aria-label="Next">
                        <span aria-hidden="true" class="text-primary">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>-->
    </div>
    <!--  -->
</div>`;
}