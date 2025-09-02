// ------------------------
// AccountCard.jsx
// ------------------------
import React from 'react';


export default function AccountCard({ accountType, balance }) {
return (
<div className="p-4 rounded-2xl shadow-md bg-white dark:bg-gray-800">
<h2 className="text-lg font-semibold text-gray-800 dark:text-gray-200">{accountType}</h2>
<p className="text-2xl font-bold text-blue-600 dark:text-blue-400">${balance}</p>
</div>
);
}