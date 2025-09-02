// ------------------------
// TransactionsTable.jsx
// ------------------------
import React from 'react';


export default function TransactionsTable({ transactions }) {
return (
<div className="p-4 rounded-2xl shadow-md bg-white dark:bg-gray-800">
<h2 className="text-lg font-semibold text-gray-800 dark:text-gray-200 mb-2">Recent Transactions</h2>
<table className="w-full text-left text-sm text-gray-700 dark:text-gray-300">
<thead>
<tr className="border-b border-gray-200 dark:border-gray-700">
<th className="py-2">Date</th>
<th>Description</th>
<th className="text-right">Amount</th>
</tr>
</thead>
<tbody>
{transactions.map((t, i) => (
<tr key={i} className="border-b border-gray-100 dark:border-gray-700">
<td className="py-2">{t.date}</td>
<td>{t.desc}</td>
<td className="text-right">{t.amount}</td>
</tr>
))}
</tbody>
</table>
</div>
);
}