import { Link } from "react-router-dom"

const Navbar = () => {
  return(
    <div>
      <Link to={"/"}><li>Domů</li></Link>
      <Link to={"/add"}><li>Přidat uživatele</li></Link>
    </div>
  )
}
export default Navbar
