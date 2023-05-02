import Video from './backvideo/video.mp4';

const Body = () => {
    
  
  
    return(
       
        <div className='main'>
        <div className="overlay"></div>
        <video src={Video} autoPlay loop muted />
    
    </div>
    )
}
export default Body;