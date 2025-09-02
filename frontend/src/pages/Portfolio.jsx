// ------------------------
// Portfolio.jsx
// ------------------------
import React from 'react';
import PortfolioList from '../components/PortfolioList';


export default function Portfolio() {
const stocks = [
{ name: 'AAPL', value: '$14,000' },
{ name: 'TSLA', value: '$9,500' },
{ name: 'GOOG', value: '$18,000' },
];


return <PortfolioList stocks={stocks} />;
}

