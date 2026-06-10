function lerEntrada(){
  var idade = document.getElementById("idade").value;
  if(idade != "" && idade != undefined && idade != null){
  document.getElementById("resp").innerHTML = `Idade informada: ${idade}`;

  votacao(idade);
}
}

function votacao(idade){
  if(idade < 18){
    document.getElementById("resp").innerHTML += ", a pessoa é menor de idade.";
  }
  else{
    document.getElementById("resp").innerHTML += ", a pessoa é maior de idade.";
  }

}