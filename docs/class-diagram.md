# Diagramme de Classes - Digital Banking

## Description du Diagramme

Ce diagramme UML représente la structure complète du modèle de données de l'application Digital Banking.

### Entités et Relations

#### Customer (Client)
- **Attributs** :
  - `id : String` - Identifiant unique du client
  - `name : String` - Nom complet du client
  - `email : String` - Adresse email du client

#### BankAccount (Compte Bancaire - Classe Abstraite)
- **Attributs** :
  - `id : String` - Identifiant unique du compte
  - `createdAt : Date` - Date de création du compte
  - `balance : double` - Solde actuel du compte
  - `status : AccountStatus` - Statut du compte (CREATED, ACTIVATED, SUSPENDED)
  - `currency : String` - Devise du compte

#### CurrentAccount (Compte Courant)
- **Hérite de** : BankAccount
- **Attributs spécifiques** :
  - `overDraft : double` - Montant du découvert autorisé

#### SavingAccount (Compte Épargne)
- **Hérite de** : BankAccount
- **Attributs spécifiques** :
  - `interestRate : double` - Taux d'intérêt annuel

#### Operation (Opération Bancaire)
- **Attributs** :
  - `id : Long` - Identifiant unique de l'opération
  - `date : Date` - Date et heure de l'opération
  - `amount : double` - Montant de l'opération
  - `type : OperationType` - Type d'opération (CREDIT, DEBIT)

### Énumérations

#### AccountStatus
- `CREATED` - Compte créé mais non activé
- `ACTIVATED` - Compte actif et opérationnel
- `SUSPENDED` - Compte suspendu temporairement

#### OperationType
- `CREDIT` - Opération de crédit (ajout d'argent)
- `DEBIT` - Opération de débit (retrait d'argent)

### Relations

1. **Customer → BankAccount** : 
   - Relation One-to-Many (1..*)
   - Un client peut posséder plusieurs comptes bancaires

2. **BankAccount → Operation** :
   - Relation One-to-Many (1..*)
   - Un compte peut avoir plusieurs opérations

3. **Héritage** :
   - CurrentAccount et SavingAccount héritent de BankAccount
   - Implémentation avec stratégie "Single Table" en JPA

### Contraintes Métier

- Un client doit avoir au moins un compte bancaire
- Le solde d'un compte courant peut être négatif (dans la limite du découvert)
- Le solde d'un compte épargne ne peut pas être négatif
- Chaque opération doit être associée à un compte bancaire valide
- Les montants des opérations doivent être positifs
