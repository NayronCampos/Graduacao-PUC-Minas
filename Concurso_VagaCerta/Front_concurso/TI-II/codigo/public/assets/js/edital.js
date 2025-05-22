document.addEventListener('DOMContentLoaded', function() {
  fetcheditals();

  document.getElementById('botaoBuscar').addEventListener('click', function() {
    const query = document.getElementById('search').value.toLowerCase();
    fetcheditals(query);
  });
});

function fetcheditals(query = '') {
  fetch('http://localhost:3000/editals')
    .then(response => response.json())
    .then(data => {
      const editalContainer = document.getElementById('edital-container');
      editalContainer.innerHTML = '';
      const filterededitals = data.filter(edital => edital.concurso.toLowerCase().includes(query));
      filterededitals.forEach(edital => {
        const editalElement = document.createElement('div');
        editalElement.className = 'edital-caixinha';
        editalElement.innerHTML = `
          <h2>${edital.concurso}</h2>
          <p>${edital.descricao}</p>
          <p>Data de Aplicação: ${edital.dataAplicacao}</p>
          <p>Número de Questões: ${edital.numeroQuestoes}</p>
          <a href="${edital.link}" target="_blank">Acessar edital</a>
        `;
        editalContainer.appendChild(editalElement);
      });
    })
    .catch(error => console.error('Error fetching editals:', error));
}