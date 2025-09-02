// ------------------------
// Transactions.jsx
// ------------------------
import React from 'react';
import TransactionsTable from '../components/TransactionsTable';


export default function Transactions() {
const transactions = [
{ date: '2025-09-01', desc: 'Grocery Store', amount: '-$54.90' },
{ date: '2025-08-28', desc: 'Salary', amount: '+$2,500.00' },
{ date: '2025-08-25', desc: 'Electricity Bill', amount: '-$120.50' },
];


return <TransactionsTable transactions={transactions} />;
}