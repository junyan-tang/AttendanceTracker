function Report() {

  // const [user, setUser] = useState(null)
  // // console.log(fstate);
  // useEffect(() => {
  //   //curUser()
  //   axios.get('http://vcm-38027.vm.duke.edu:8080/profile', { headers:{ 'Authorization': `Bearer ${localStorage.getItem('token')}` } })
  //   .then((response) => {
  //     console.log("profile" + response);
  //     setUser(response.data);
  //   }).catch(error => {
  //     console.error("error in profile"+ error);
  //   })
  // }, [])

  // if (!user) {
  //     return <div>Fetching data</div>;
  // }

  // return (
  //   <div>
  //     {user.identity === "professor"
  //       ? <AddAttendance />
  //       : <TakeAttendance netid={user.netid}/>
  //     }
  //   </div>
  // )

}

export default Report;