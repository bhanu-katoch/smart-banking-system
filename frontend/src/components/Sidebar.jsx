import { Link } from "react-router-dom";

const Sidebar = () => {
  return (
    <div className="w-64 bg-white shadow-lg p-4">
      <h2 className="text-2xl font-bold mb-6">Smart Banking</h2>
      <ul className="space-y-4">
        <li><Link to="/">Dashboard</Link></li>
        <li><Link to="/accounts">Accounts</Link></li>
        <li><Link to="/portfolio">Portfolio</Link></li>
        <li><Link to="/transactions">Transactions</Link></li>
        <li><Link to="/budget">Budget</Link></li>
      </ul>
    </div>
  );
};

export default Sidebar;
