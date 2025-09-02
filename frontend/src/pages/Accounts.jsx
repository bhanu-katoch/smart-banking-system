// ------------------------
// Accounts.jsx
// ------------------------
import React from 'react';
import AccountCard from '../components/AccountCard';


export default function Accounts() {
const accounts = [
{ accountType: 'Savings', balance: 5400 },
{ accountType: 'Checking', balance: 1200 },
{ accountType: 'FD', balance: 50000 },
];


return (
<div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
{accounts.map((acc, i) => <AccountCard key={i} {...acc} />)}
</div>
);
}

