       /*
            Comandos de saída.
            variável de entrada
            template string
        */

            var produto = prompt("Send a product:");
            var descricao = prompt("Send a description:");
            var preco = prompt("Send a pryce:");

            document.write("<img src='../capa.png' alt='capa'>");
            document.write(`<h1 class = "titulo">${produto}</h1>`);
            document.write(`<article class = "titulo">${descricao}</article>`);
            document.write(`<aside class = "preco">${preco}</aside>`);
       
            alert(`Hello, ${produto}`);
            console.log(`Hello, ${produto}`);