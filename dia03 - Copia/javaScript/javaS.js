function entrada(){
  var idade = document.getElementById("idade").value;
  document.getElementById("resp").innerHTML = `Idade informada`;
}

function votacao(idade){
  if(idade < 18){
    document.getElementById("Resp").innerHTML += "A pessoa é menor de idade.";
  }
}