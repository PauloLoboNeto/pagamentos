# language: pt
Funcionalidade: Pagamento

  Cenario: Realizar Pagamento
    Quando realizar um pagamento
    Entao o pagamento e registrado com sucesso
    E deve ser apresentado

  Cenario: Buscar Pagamento do Pedido
    Dado que um pedido ja foi realizado
    Quando efetuar a busca do pagamento do pedido
    Entao o pagamento do pedido e exibido com sucesso