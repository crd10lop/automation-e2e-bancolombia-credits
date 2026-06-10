# language: es
Feature: Simulacion de creditos en el portal de Bancolombia
  Como usuario interesado en un credito
  Quiero usar los simuladores del portal de Bancolombia
  Para conocer la cuota aproximada segun el monto y el plazo que necesito

  Background:
    Given que el usuario se encuentra en el portal de creditos de Bancolombia

  Scenario Outline: Simulacion del credito de libre inversion con diferentes montos y plazos
    When el usuario navega al simulador de libre inversion
    And acepta continuar con la simulacion de libre inversion
    And selecciona que si sabe el monto que necesita
    And ingresa un monto de "<monto>" y un plazo de "<plazo>" meses para libre inversion
    Then el resultado de la simulacion de libre inversion debe ser visible

    Examples:
      | monto     | plazo |
      | 5000000   | 48    |
      | 10000000  | 60    |
      | 20000000  | 84    |

  Scenario Outline: Simulacion del credito de libranza con diferentes montos y plazos
    When el usuario navega al simulador de libranza
    And ingresa un monto de "<monto>" y un plazo de "<plazo>" meses para libranza
    And ingresa la fecha de nacimiento "<fechaNacimiento>" para libranza
    And hace clic en simular para libranza
    Then el resultado de la simulacion de libranza debe ser visible

    Examples:
      | monto      | plazo | fechaNacimiento |
      | 5000000    | 72    | 15/06/1990      |
      | 15000000   | 84    | 20/03/1985      |
      | 30000000   | 96    | 10/11/1992      |

  Scenario Outline: Simulacion del credito Crediagil con diferentes montos y plazos
    When el usuario navega al simulador de Crediagil
    And acepta continuar con la simulacion de Crediagil
    And selecciona que si sabe el monto que necesita en Crediagil
    And ingresa un monto de "<monto>" y un plazo de "<plazo>" meses para Crediagil
    Then el resultado de la simulacion de Crediagil debe ser visible

    Examples:
      | monto    | plazo |
      | 2000000  | 12    |
      | 5000000  | 24    |
      | 8000000  | 36    |
