// ------------------------
// BudgetCard.jsx
// ------------------------
import React from 'react';


export default function BudgetCard({ spent, budget }) {
const pct = Math.min(100, Math.round((spent / budget) * 100));
return (
<div className="p-4 rounded-2xl shadow-md bg-white dark:bg-gray-800">
<h2 className="text-lg font-semibold text-gray-800 dark:text-gray-200">Monthly Budget</h2>
<p className="mt-2 text-gray-600 dark:text-gray-400">Spent: ${spent} / ${budget}</p>
<div className="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-3 mt-2">
<div className="bg-green-500 h-3 rounded-full" style={{ width: `${pct}%` }} />
</div>
</div>
);
}