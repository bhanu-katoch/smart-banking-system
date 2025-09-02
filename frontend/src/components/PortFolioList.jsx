// ------------------------
// PortfolioList.jsx
// ------------------------
import React from 'react';


export default function PortfolioList({ stocks }) {
return (
<div className="p-4 rounded-2xl shadow-md bg-white dark:bg-gray-800">
<h2 className="text-lg font-semibold text-gray-800 dark:text-gray-200 mb-2">Stock Portfolio</h2>
<ul className="space-y-1">
{stocks.map((s, i) => (
<li key={i} className="flex justify-between text-gray-700 dark:text-gray-300">
<span>{s.name}</span>
<span>{s.value}</span>
</li>
))}
</ul>
</div>
);
}