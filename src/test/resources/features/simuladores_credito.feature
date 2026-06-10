Feature: Simulacion de creditos en el portal de Bancolombia
  Como usuario interesado en un credito
  Quiero usar los simuladores del portal de Bancolombia
  Para conocer la cuota aproximada segun el monto y el plazo que necesito

  Background:
    Given que el usuario se encuentra en el portal de creditos de Bancolombia

  Scenario Outline: Simulacion del credito de libre inversion con diferentes datos
    When el usuario navega al simulador de libre inversion
    And simula un credito de libre inversion con un monto de "<monto>", un plazo de "<plazo>" meses y fecha de nacimiento "<fechaNacimiento>"
    Then el resultado de la simulacion de libre inversion debe ser visible

    Examples:
      | monto    | plazo | fechaNacimiento |
      | 5000000  | 48    | 15/06/1990      |
      | 10000000 | 60    | 20/03/1985      |
      | 20000000 | 84    | 10/11/1992      |

  Scenario Outline: Simulacion del credito de libranza con diferentes datos
    When el usuario navega al simulador de libranza
    And simula un credito de libranza con un monto de "<monto>", un plazo de "<plazo>" meses y fecha de nacimiento "<fechaNacimiento>"
    Then el resultado de la simulacion de libranza debe ser visible

    Examples:
      | monto    | plazo | fechaNacimiento |
      | 5000000  | 72    | 15/06/1990      |
      | 15000000 | 84    | 20/03/1985      |
      | 30000000 | 96    | 10/11/1992      |

  Scenario Outline: Simulacion del cupo rotativo Crediagil con diferentes datos
    When el usuario navega al simulador de Crediagil
    And simula un cupo Crediagil con un monto de "<monto>" y fecha de nacimiento "<fechaNacimiento>"
    Then el resultado de la simulacion de Crediagil debe ser visible

    Examples:
      | monto   | fechaNacimiento |
      | 2000000 | 15/06/1990      |
      | 5000000 | 20/03/1985      |
      | 8000000 | 10/11/1992      |
