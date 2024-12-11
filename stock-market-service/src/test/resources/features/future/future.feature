Feature: Future Contracti
  Preko ovih testova se testira kupovina future contracta, kao i kreiranje i prihvatanje porudzbina

  Scenario: Kupovina future contracta od strane supervisora
    When Kada radnik kupi future contract sa IDem "1" pomocu racuna firme "444000000000000022"
    Then Firma postaje vlasnik contracta
