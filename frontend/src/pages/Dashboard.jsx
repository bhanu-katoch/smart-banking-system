// ------------------------
// Dashboard.jsx
// ------------------------
import React from 'react';
import AccountCard from '../components/AccountCard';
import PortfolioList from '../components/PortfolioList';
import TransactionsTable from '../components/TransactionsTable';
import BudgetCard from '../components/BudgetCard';


export default function Dashboard() {
const accounts = [
{ accountType: 'Savings', balance: 5400 },
{ accountType: 'Checking', balance: 1200 },
];
const stocks = [
{ name: 'AAPL', value: '$14,000' },
{ name: 'TSLA', value: '$9,500' },
];
const transactions = [
{ date: '2025-09-01', desc: 'Grocery Store', amount: '-$54.90' },
{ date: '2025-08-28', desc: 'Salary', amount: '+$2,500.00' },
];


return (
<div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
{accounts.map((acc, i) => <AccountCard key={i} {...acc} />)}
<PortfolioList stocks={stocks} />
<TransactionsTable transactions={transactions} />
<BudgetCard spent={1200} budget={2000} />
</div>
);
}