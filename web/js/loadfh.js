async function loadHeader(page) {
    let nameElement = `<div class="col-4 col-lg-auto d-flex justify-content-center justify-content-lg-start mt-2">
                                        <span class="fw-bold opacity-50 sr" onclick="window.location='sign-in.html'">Sign In or Register</span>
                                    </div>`;

    const response = await fetch("CheckSignIn");
    if (response.ok) {

        const json = await response.json();
        if (json.success) {
            const name = json.content;
            const nameShort = name.length > 10 ? name.substring(0, 15) + "..." : name;
            nameElement = `<div class="col-4 col-lg-auto d-flex justify-content-end justify-content-lg-start mt-2">
                                        <span class="fw-bold opacity-50 shake"><b>Welcome </b>` + nameShort + `</span>
                                    </div>
                                    <div class="col-4 col-lg-auto d-flex justify-content-center justify-content-lg-start mt-2">
                                        <span class="fw-bold opacity-50 so" onclick="window.location='SignOut'">Sign Out</span>
                                    </div>`;

        } else {

        }
    }


    const header = `<!DOCTYPE html>
    <html lang="en">
    
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="bs/bootstrap.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
       <link rel="stylesheet" href="style.css" />
    </head>
    
    <body>
        <div class="col-12 bg-opacity-25 p-1 sticky-lg-top" style="background-color: rgb(85, 107, 47);">
            <div class="row">
                <div class="col-12 col-lg-2">
                    <div class="row align-items-center hvr" onclick="window.location= 'index.html'">
                        <div class="col-12 col-lg-2 d-flex d-lg-block justify-content-center mt-1"> <img src="./images/bookstore_logo.png" style="height: 35px;"></div>
                        <div class="opacity-75 col-12 col-lg-10 d-flex d-lg-block gw-iconText justify-content-center mt-1">BookPalace</div>
                    </div>
                </div>
                <div class="col-12 d-block d-lg-none">
                    <hr>
                </div>
                <div class="col-12 col-lg-10">
                    <div class="row mt-1">
                        <div class="col-12 col-lg-5 px-3">
                            <div class="row">
                                    ` + nameElement + `
                                <div class="col-4 col-lg-auto d-flex justify-content-center justify-content-lg-start mt-2">
                                    <span class="text-lg-start fw-bold opacity-50 hc" id="homeAndHc" onclick="window.location = '#'">Help and Contact</span>
                                </div>
                            </div>
                        </div>
                        <div class="col col-lg-2 d-none d-lg-block"></div>
                        <div class="col-12 col-lg-5 px-3 mt-2 mt-lg-0">
                            <div class="row">
                                <div class="col-4 d-flex justify-content-center justify-content-lg-end mt-2 opacity-50 hvr" onclick="window.location='add-product.html'">Sell</div>
                                <div class="col-4 d-flex justify-content-center justify-content-lg-end mb-1">
                                    <div class="col-8 offset-2 dropdown">
                                        <button class="btn bg-transparent dropdown-toggle border border-2 border-success" style="color: antiquewhite;" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                            ` + page + `
                                        </button>
                                        <ul class="dropdown-menu bg-opacity-50 bg-success">
                                            <li><a class="dropdown-item" href="my-profile.html">My Profile</a></li>
                                            <li><a class="dropdown-item" href="my-products.html">My Products</a></li>
                                            <!--<li><a class="dropdown-item" href="#">Wishlist</a></li>-->
                                            <!--<li><a class="dropdown-item" href="#">Purchase History</a></li>-->
                                            <!--<li><a class="dropdown-item" href="#">Message</a></li>-->
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-4 d-flex justify-content-center">
                                    <div class="col-4 cart-icon hvr" onclick="window.location = 'cart.html'"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="script.js"></script>
        <script src="bs/bootstrap.bundle.js"></script>
        <script src="bs/bootstrap.js"></script>
    </body>
    
    </html>
    <!-- Mohamed Hanas Abdur Rahman | @nxt.genar7@gmail.com | hssxar7 -->`;
    document.getElementById("header").innerHTML = header;
}


function loadFooter() {
    const footer = `<!DOCTYPE html>
    <html lang="en">
    
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="bootstrap.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
        <link rel="stylesheet" href="style.css" />
    </head>
    
    <body>
        <footer class="text-white p-4" style="background-color: rgb(85, 107, 47);">
    
            <div class="col-12 text-md-start">
                <div class="row text-sm-center text-md-start">
    
                    <div class="col-12 col-md-3 col-lg-3 mx-auto">
                        <h5 class="text-uppercase  text-warning ">Book Palace</h5>
                        <p>Here we are the BookPalace.lk&trade; Buy, sell, and explore a world of books at your fingertips. Discover your next read or share your collection with fellow book lovers today!</p>
                        <p class="text-black fw-bold"> by Abdur Rahman</p>
                    </div>
    
                    <div class="col-12 col-md-3 col-lg-3 mx-auto mt-4">
                        <p>Copyright &copy; 2024 BookPalace All Rights Reserved</p>
                    </div>
    
                    <div class="col-12 col-md-3 col-lg-3 mx-auto mt-4">
                        <div class="text-center text-md-start">
    
                            <ul class="list-unstyled list-inline">
    
                                <li class="list-inline-item">
                                    <a href="#" class="form-floating text-white">
                                        <i class="bi bi-facebook" style="font-size: 22px;"></i>
                                    </a>
                                </li>
    
                                <li class="list-inline-item">
                                    <a href="#" class="form-floating text-white">
                                        <i class="bi bi-twitter" style="font-size: 22px;"></i>
                                    </a>
                                </li>
    
                                <li class="list-inline-item">
                                    <a href="#" class="form-floating text-white">
                                        <i class="bi bi-whatsapp" style="font-size: 22px;"></i>
                                    </a>
                                </li>
    
                                <li class="list-inline-item">
                                    <a href="#" class="form-floating text-white">
                                        <i class="bi bi-linkedin" style="font-size: 22px;"></i>
                                    </a>
                                </li>
    
                                <li class="list-inline-item">
                                    <a href="#" class="form-floating text-white">
                                        <i class="bi bi-youtube" style="font-size: 22px;"></i>
                                    </a>
                                </li>
    
                            </ul>
    
                        </div>
                    </div>
    
                    <div class="col-12 col-md-3 col-lg-3 mx-auto">
    
                        <h5 class="text-uppercase text-warning mb-4">Contact Us</h5>
    
                        <p><i class="bi bi-house-fill"></i> Galle city, Galle, Sri Lanka</p>
                        <p><i class="bi bi-at"></i> bookpalace@gmail.com</p>
                        <p><i class="bi bi-telephone-fill"></i> +94112 4445558</p>
                        <p><i class="bi bi-printer-fill"></i> +94112 4445558</p>
    
                    </div>
    
                    <hr class="mb-4" />
    
                </div>
            </div>
    
        </footer>
    </body>
    
    </html>
    <!-- Mohamed Hanas Abdur Rahman | @nxt.genar7@gmail.com | hssxar7 -->`;
    document.getElementById("footer").innerHTML = footer;
}


