// ------------------------
// Navbar.jsx
// ------------------------
import React from 'react';
import { Sun, Moon } from 'lucide-react';


export default function Navbar({ dark, toggleDark }) {
return (
<header className="flex justify-between items-center p-4 bg-white dark:bg-gray-800 shadow-md">
<h1 className="text-2xl font-bold text-gray-900 dark:text-white">Smart Banking Dashboard</h1>
<button
onClick={toggleDark}
className="p-2 rounded-full bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-gray-200"
>
{dark ? <Sun size={20} /> : <Moon size={20} />}
</button>
</header>
);
}