    // handle show productImgColor
    const productImgDetail = () => {
        document.querySelector("#tb2").classList.add("table2")
        document.querySelector("#tb1").classList.add("table1")
        document.querySelector("#tb2").classList.remove("tabledefault")
        document.querySelector("#tb1").classList.remove("tabledefault")
    }
    // handle show product
    const product = () => {
        document.querySelector("#tb2").classList.add("tabledefault")
        document.querySelector("#tb1").classList.add("tabledefault")
        document.querySelector("#tb2").classList.remove("table2")
        document.querySelector("#tb1").classList.remove("table1")
    }
    const Addrecord = () => {
        document.querySelector("#createForm").setAttribute("style", "display : block")
        document.querySelector("#updateProduct").removeAttribute("style")
        document.querySelector("#ProductID").setAttribute("style", "")

        handle.classList.toggle("handle")
    }
    const Addrecord2 = () => {
        document.querySelector("#createButtonProduct").setAttribute("style", "display : block");
        handle2.classList.toggle("handle2")

    }
    const Addrecord3 = () => {
        document.querySelector("#createButtonProductColor").setAttribute("style", "display : block");
        handle4.classList.toggle("handle4")

    }
    const handlePagination = () => {
        let listProduct = document.getElementsByName("listProductByCategory")
        let listProductChecked = []
        listProduct.forEach(item => item.checked && listProductChecked.push(item.value))
        localStorage.setItem('list', listProductChecked);
        // window.location.href = "http://localhost:8080/admin/product?type=category&size=5&page=2"
        // th:href="@{${url}(size=${productPage.size}, page=1 )}"
    }
    const listProductByCategory = document.querySelector("#listProductByCategory")
    window.addEventListener("load", () => {
        location.href.includes("http://localhost:8080/admin/product?type=category&size=5") || localStorage.removeItem("list")
        let listProduct = document.getElementsByName("listProductByCategory")
        let listProductChecked = []
        if (localStorage.getItem("list") != null) {
            listProductChecked = localStorage.getItem("list").split(",")
        }
        listProduct.forEach(item => {
            listProductChecked.forEach(itemcheck => {
                if (item.value == itemcheck) {
                    item.checked = true
                }
            })
        })
    })
    //handle toogle form add product
    const showFormFilter = () => {
        handle3.classList.add("handle3")
    }
    const handleDeleteProductImg = () => {
        productImgDetail()
    }
    // handle call Form add product
    const createProduct = () => {
        let productId = document.querySelector("#ProductID")
        let productName = document.querySelector("#productName")
        let createDate = document.querySelector("#createdDate")
        let price = document.querySelector("#viewcount")
        let desc = document.querySelector("#description")
        let avtImg = document.querySelector("#avt")
        let allColumns = [productId, productName, createDate, price, desc, avtImg]
        let a = true
        // kiểm tra dữ liệu nhập vào
          allColumns.every(item => {
            switch (item.value) {
                case "":
                    message(`Bạn không thể thêm sản phẩm nếu thiếu ${item.getAttribute("placeholder")}`)
                   a = false
                    break;
                case avtImg.value:
                    if (item.getAttribute("src") == "") {
                        message(`Bạn không thể thêm sản phẩm nếu thiếu ${item.getAttribute("class")} `)
                        a = false;
                    }
                    break;
                case price.value:
                    if (/^\d+$/.test(item.value) == false) {
                        message(`Giá sản phẩm phải là 1 số nguyên dương`)
                        a = false
                    }
                    break;
                case createDate.value:
                    switch (Date.parse(createDate.value)) {
                        case NaN:
                            message(`Ngày thêm sản phẩm không đúng định dạng `)
                            a = false
                            break;
                        default:
                            if(new Date(createDate.value) > new Date()){
                                message(`Ngày thêm sản phẩm không được lớn hơn ngày hiện tại`)
                                a = false
                            }
                            break;
                    }
                    break;
                default :
                    break;
            }
            return a
          })
        if (a == true) {
            $('#submit').prop('action', '/admin/product/create')
            $("#submit").submit()
        }
    }

    function message(value) {
        document.querySelector(".toast-body").innerHTML = value
        const status = document.querySelector("#status")
        status.classList.add("done_delete")
        setTimeout(() => status.classList.remove("done_delete"), 4000)
    }

    //handle call form add product color and product img
    const createImgProduct = () => {
        $('#submitImgAndColor').prop('action', '/admin/product/createImgProduct');
        $('#submitImgAndColor').submit();

    }
    const createColorProduct = () => {
        let avaible = document.querySelector("#avaible").value
        if (avaible != null && parseInt(avaible) != NaN && avaible > 0) {
            $('#submitColor').prop('action', '/admin/product/createColorProduct');
            $('#submitColor').submit();
        } else {
            document.querySelector(".toast-body").innerHTML = "Số lượng bạn nhập không hợp lệ"
            const status = document.querySelector("#status")
            status.classList.add("done_delete")
            setTimeout(() => status.classList.remove("done_delete"), 4000)
        }
    }

    const updateImgProduct = () => {
        let productId = document.querySelector("#ProductID")
        let productName = document.querySelector("#productName")
        let createDate = document.querySelector("#createdDate")
        let price = document.querySelector("#viewcount")
        let desc = document.querySelector("#description")
        let avtImg = document.querySelector("#avt")
        let allColumns = [productId, productName, createDate, price, desc, avtImg]
        let a = true
        // kiểm tra dữ liệu nhập vào
        allColumns.every(item => {
            switch (item.value) {
                case "":
                    message(`Bạn không thể chỉnh sửa sản phẩm nếu thiếu ${item.getAttribute("placeholder")}`)
                    a = false
                    break;
                case avtImg.value:
                    if (item.getAttribute("src") == "") {
                        message(`Bạn không thể chỉnh sửa sản phẩm nếu thiếu ${item.getAttribute("class")} `)
                        a = false;
                    }
                    break;
                case price.value:
                    if (/^\d+$/.test(item.value) == false) {
                        message(`Giá sản phẩm phải là 1 số nguyên dương`)
                        a = false
                    }
                    break;
                case createDate.value:
                    switch (Date.parse(createDate.value)) {
                        case NaN:
                            message(`Ngày thêm sản phẩm không đúng định dạng `)
                            a = false
                            break;
                        default:
                            if(new Date(createDate.value) > new Date()){
                                message(`Ngày thêm sản phẩm không được lớn hơn ngày hiện tại`)
                                a = false
                            }
                            break;
                    }
                    break;
                default :
                    break;
            }
            return a
        })
        if (a == true){
            $('#submitImgAndColor').prop('action', '/admin/product/updateProductImgAndColor');
            $('#submitImgAndColor').submit();
        }
    }
    //handle call update product
    const updateForm = () => {
        $('#submit').prop('action', '/admin/product/update');
        $("#submit").submit();
    }
    const productCate = () => {
        $('#listProductByCategory').prop('action', '/admin/product?type=category&size=5&page=1');
        $("#listProductByCategory").submit();
    }
    //edit productImg form
    const editProductImg = () => {
        location.href.includes("http://localhost:8080/admin/product/editProductImg") && productImgDetail()
    }
    window.addEventListener("load", () => {
        location.href.includes("size2=") && productImgDetail()
        location.href.includes("/product") && document.querySelector("#productIcon").setAttribute("style", "color:#fff; scale:1.1")
        location.href === "http://localhost:8080/admin" && document.querySelector("#admin").setAttribute("style", "color:#fff; scale:1.1")
        let list = document.getElementsByName("listProductColor")
        let prevProductID = localStorage.getItem("currentProductID")
        list.forEach(item => {
            if (item.getAttribute("placeholder") == item.getAttribute("value")) {
                item.setAttribute("selected", "selected")
                localStorage.setItem("currentProductID", item.getAttribute("value"))
            }
        })
        let currentProductID = localStorage.getItem("currentProductID")
        if (prevProductID != currentProductID) {
            productImgDetail()
            Addrecord2()
        }

    })
    //handle delete message after 5s
    $(".done_delete").length == 1 && setTimeout(() => {
        const status = document.querySelector("#status")
        status.classList.remove("done_delete")
    }, 5000)

    //handle delete message

    function submitlistProduct(id) {
        let idprd = document.querySelector("#ProductIDList").value
        let list = JSON.parse(localStorage.getItem("listProductColor"))
        let list2 = []
        list.forEach(item => item["productID"] == idprd && list2.push(item))
        let comboboxColor = document.querySelector("#ListColorProduct")
        let length = comboboxColor.children.length
        for (i = length - 1; i >= 0; i--) {
            comboboxColor.removeChild(comboboxColor.children[i])
        }

        list2.forEach(item => {
            let node = document.createElement("option")
            node.setAttribute("value", item["colorID"])
            node.setAttribute("id", item["colorID"])
            node.innerHTML = item["color_name"]
            comboboxColor.appendChild(node)
        })
        document.querySelector("#IdProduct").children[1].setAttribute("value", idprd)
    }

    const getProductID = () => {
        //lấy product id cho table product color
        let currentProductId = document.querySelector("#ProductIDList1")
        let inputProductID = document.querySelector("#idPrd")
        inputProductID.setAttribute("value", currentProductId.value)
        document.querySelector(".prdIdInput").setAttribute("style", "margin-top: 54px; opacity: 1")
    }

    function demo(currentval) {
        let valueProduct = currentval.parentElement.parentElement
        document.querySelector("#ProductID").value = valueProduct.children[0].getAttribute("id")
        document.querySelector("#ProductID").setAttribute("style", "pointer-events:none;")
        document.querySelector("#productName").value = valueProduct.children[0].innerHTML
        document.querySelector("#createdDate").value = valueProduct.children[3].innerHTML.split("-").reverse().join("-")
        document.querySelector("#viewcount").value = valueProduct.children[1].innerHTML
        document.querySelector("#categoryid").value = valueProduct.children[4].getAttribute("alt")
        document.querySelector("#description").value = valueProduct.children[6].children[0].innerHTML
        document.querySelector("#avt").setAttribute("src", valueProduct.children[2].children[0].getAttribute("src"))
        document.querySelector("#handle").classList.add("handle")
        document.querySelector("#updateProduct").setAttribute("style", "display:block")
        document.querySelector("#createForm").removeAttribute("style")
    }
    async function showFormProductColor (val) {
      let currentVal =  "#body"+ val.children[0].getAttribute("id")
       let tbodyProductColorbyProductID = document.querySelector(currentVal)
      await ListproductColor(val.children[0].getAttribute("id"))
        let listProductColorbyProductID = JSON.parse(localStorage.getItem("listProductColorByProductId"))
        let span = document.createElement("span")
        console.log(tbodyProductColorbyProductID.children.length)
       if (tbodyProductColorbyProductID.children.length == 0){
           await listProductColorbyProductID.forEach( item => {
               tbodyProductColorbyProductID.innerHTML += `<tr>
                                            <td>${item["product"].name}</td>
                                            <td>${item["color_name"]}</td>
                                            <td style="position: relative"> <span style=" position: absolute; height: 40px; width: 40px; bottom: 3px;border-radius: 50% ; left:65px; background-color: ${item["colorhex"]}"> </span></td>
                                            <td>${ item["available"]}</td>
                                            <td> ${item["product"].deprecated == true ? 'Ngưng bán' : 'Đang bán'}</td>
                                            <td> Edit</td>
                                        </tr>`
           })
       }
           let lastChild = val.children[val.children.length - 1]
           document.querySelector(".activeTable") != null && lastChild.classList.remove("activeTable")
           lastChild.getAttribute("handleexit") == null ? lastChild.classList.add("activeTable") : lastChild.removeAttribute("handleExit")

   }
    function exitFormOrderDetail(){
        let dv = document.querySelector(".activeTable")
        dv.classList.remove("activeTable")
         dv.setAttribute("handleexit", "true")
    }
    async function ListproductColor(id) {
        const response = await fetch("/admin/product/listProductColorByProductId/"+id);
        const data = await response.json();
        console.log('hello')
        console.log(data)
        localStorage.setItem("listProductColorByProductId", JSON.stringify(data))
    }
    window.addEventListener("load", () => {
        location.href.includes("/order") && document.querySelector(".oderIcon").setAttribute("style", "color:#fff; scale:1.1")
    })