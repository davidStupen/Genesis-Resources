import axios from "axios"

const DeleteBtn = (props) => {
  const handlerDelete = async () => {
    try{
      await axios.delete(`http://localhost:8080/api/v1/users/${props.clickId}`)
      props.onclick()
    } catch(e){
      console.error(e)
    }
  }
  return(
    <div>
      <button onClick={handlerDelete}>Smazat</button>
    </div>
  )
}
export default DeleteBtn
