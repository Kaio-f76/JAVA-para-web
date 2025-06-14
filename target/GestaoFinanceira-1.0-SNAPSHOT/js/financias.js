
// Função para inicializar o gráfico de receitas e despesas
function initChart(receitas, despesas, meses, metas) {
    console.log(meses); 
    
    const metasNumericas = metas.map(meta => {
        const metaNumerica = parseFloat(meta);
        return isNaN(metaNumerica) ? 0 : metaNumerica;
    });
    
    const ctx = document.getElementById('financeChart').getContext('2d');
    const financeChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: meses,
            datasets: [
                {
                    label: 'Receita',
                    data: receitas,
                    backgroundColor: '#1abc9c',
                }, 
                {
                    label: 'Despesa',
                    data: despesas,
                    backgroundColor: '#e74c3c',
                },
                {
                    label: 'Meta',
                    data: metasNumericas, // Adiciona as metas no gráfico
                    backgroundColor: '#f39c12', // Cor da meta
                }
            
                
            ]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    // Chama a função para atualizar a barra de progresso
    updateProgress(receitas, despesas, metas);
}

function updateProgress(receitas, despesas, metas) {
    console.log('Meses:', meses);  
    console.log('Receitas:', receitas);  
    console.log('Despesas:', despesas);  
    console.log('Metas:', metas);

    // Verificando se as receitas, despesas e metas têm o mesmo comprimento
    if (receitas.length !== despesas.length || receitas.length !== metas.length) {
        console.error('As listas de receitas, despesas e metas não têm o mesmo tamanho!');
        return;
    }

    // Array para armazenar as porcentagens de economia para cada mês
    let porcentagensEconomia = [];
    let totalEconomizado = 0;
    let totalMeta = 0;

    // Calculando a economia mês a mês
    for (let i = 0; i < receitas.length; i++) {
        const totalReceita = receitas[i];
        const totalDespesa = despesas[i];
        const meta = parseFloat(metas[i]);

        // Calcula a economia e a porcentagem de economia para o mês
        let economia = totalReceita - totalDespesa;
        let porcentagemEconomia = (economia / totalReceita) * 100;

        // Garantir que a porcentagem esteja entre 0 e 100
        porcentagemEconomia = Math.max(0, Math.min(porcentagemEconomia, 100));

        // Armazena o valor da porcentagem para este mês
        porcentagensEconomia.push(porcentagemEconomia);

        // Soma o valor economizado no mês para o total
        totalEconomizado += economia;

        // Soma o valor da meta para calcular a meta total
        if (!isNaN(meta)) {
            totalMeta += meta;
        }
    }

    // calcula a média da porcentagem de economia de todos os meses para exibir na barra de progresso
    const porcentagemMedia = porcentagensEconomia.reduce((a, b) => a + b, 0) / porcentagensEconomia.length;

    console.log('Porcentagens de Economia por Mês:', porcentagensEconomia);
    console.log('Porcentagem Média de Economia:', porcentagemMedia);
    console.log('Total Economizado em Dinheiro:', totalEconomizado);
    console.log('Total das Metas:', totalMeta);

    // Calcular o total em dinheiro economizado em relação à meta
    let totalEconomizadoEmRelacaoMeta = totalEconomizado - totalMeta;

    console.log('Total Economizado em Dinheiro em Relação à Meta:', totalEconomizadoEmRelacaoMeta);

    // Atualiza a barra de progresso com a porcentagem média de economia
    document.getElementById('progress-bar').value = porcentagemMedia;
    document.getElementById('progress-text').innerText = `${Math.round(porcentagemMedia)}% economizado`;

    // Atualiza o valor economizado em dinheiro
    const saldoElement = document.getElementById('saldo');
    if (totalEconomizado < 0) {
        document.getElementById('saldo').innerText = `Saldo Negativo de : R$ ${Math.abs(totalEconomizado).toFixed(2)}`;
        saldoElement.style.color = 'red';  
    } else {
        document.getElementById('saldo').innerText = `Saldo Positivo de: R$ ${totalEconomizado.toFixed(2)}`;
        saldoElement.style.color = '#7FFF00'; // Verde
    }

    // Exibe o total economizado em dinheiro em relação à meta
    const economiaEmRelacaoMetaElement = document.getElementById('economia-em-relacao-meta');
    economiaEmRelacaoMetaElement.innerText = `Economia em relação à meta: R$ ${totalEconomizadoEmRelacaoMeta.toFixed(2)}`;

    // Inicialize o gráfico com os dados passados
    // initChart(receitas, despesas, meses);
}



/*
function updateProgress(receitas, despesas) {
    console.log('Meses:', meses);  
    console.log('Receitas:', receitas);  
    console.log('Despesas:', despesas);  

    // Verificando se as receitas e despesas têm o mesmo comprimento no caso o mes
    if (receitas.length !== despesas.length) {
        console.error('As listas de receitas e despesas não têm o mesmo tamanho!');
        return;
    }

    // Array para armazenar as porcentagens de economia para cada mês
    let porcentagensEconomia = [];
    let totalEconomizado = 0;

    // Calculando a economia mês a mês
    for (let i = 0; i < receitas.length; i++) {
        const totalReceita = receitas[i];
        const totalDespesa = despesas[i];

        // Calcula a economia e a porcentagem de economia para o mês
        let economia = totalReceita - totalDespesa;
        let porcentagemEconomia = (economia / totalReceita) * 100;

        // Garantir que a porcentagem esteja entre 0 e 100
        porcentagemEconomia = Math.max(0, Math.min(porcentagemEconomia, 100));

        // Armazena o valor da porcentagem para este mês
        porcentagensEconomia.push(porcentagemEconomia);

        // Soma o valor economizado no mês para o total
        totalEconomizado += economia;
    }

    // Agora, podemos calcular a média da porcentagem de economia de todos os meses para exibir na barra de progresso
    const porcentagemMedia = porcentagensEconomia.reduce((a, b) => a + b, 0) / porcentagensEconomia.length;

    console.log('Porcentagens de Economia por Mês:', porcentagensEconomia);
    console.log('Porcentagem Média de Economia:', porcentagemMedia);
    console.log('Total Economizado em Dinheiro:', totalEconomizado);

    // Atualiza a barra de progresso com a porcentagem média de economia
    document.getElementById('progress-bar').value = porcentagemMedia;
    document.getElementById('progress-text').innerText = `${Math.round(porcentagemMedia)}% economizado`;

    // Atualiza o valor economizado em dinheiro
    //document.getElementById('total-economizado').innerText = `Total Economizado: R$ ${totalEconomizado.toFixed(2)}`;
    
    const saldoElement = document.getElementById('saldo');
    if (totalEconomizado < 0) {
        document.getElementById('saldo').innerText = `Saldo Negativo de : R$ ${Math.abs(totalEconomizado).toFixed(2)}`;
        saldoElement.style.color = 'red';  

    } else {
        document.getElementById('saldo').innerText = `Saldo Positivo de: R$ ${totalEconomizado.toFixed(2)}`;
        saldoElement.style.color = '#7FFF00'; // é um verde  
    }

    // Inicialize o gráfico com os dados passados
    //initChart(receitas, despesas, meses);
}
*/
