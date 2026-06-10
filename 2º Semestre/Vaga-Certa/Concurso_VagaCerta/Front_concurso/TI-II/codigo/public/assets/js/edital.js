function fetchEditais(query = '') {
  fetch('http://localhost:3000/editais')
    .then(response => response.json())
    .then(data => {
      console.log("Dados recebidos:", data); // <- debug aqui

      const editalContainer = document.getElementById('edital-container');
      editalContainer.innerHTML = '';

      const filteredEditais = data.filter(edital =>
        edital.nome.toLowerCase().includes(query)
      );

      filteredEditais.forEach(edital => {
        const editalElement = document.createElement('div');
        editalElement.className = 'edital-caixinha';
        editalElement.innerHTML = `
          <h2>${edital.nome}</h2>
          <p>Data de Publicação: ${edital.data_publi}</p>
          <a href="${edital.arquivo}" target="_blank">Acessar edital</a>
        `;
        editalContainer.appendChild(editalElement);
      });
    })
    .catch(error => console.error('Erro ao buscar editais:', error));
}
