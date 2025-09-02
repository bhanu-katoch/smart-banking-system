// ------------------------
// Budget.jsx
// ------------------------
import React from 'react';
import BudgetCard from '../components/BudgetCard';


export default function Budget() {
return (
<div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
<BudgetCard spent={1200} budget={2000} />
<BudgetCard spent={500} budget={1000} />
<BudgetCard spent={800} budget={1500} />
</div>
);
}