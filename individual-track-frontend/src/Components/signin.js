import React from "react";
import { useNavigate } from "react-router";

import AuthService from "../Logic/AuthService";


import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';



  const useStyles = makeStyles((theme) => ({
    paper: {
      marginTop: theme.spacing(8),
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
    },
    avatar: {
      margin: theme.spacing(1),
      backgroundColor: theme.palette.secondary.main,
    },
    form: {
      width: '100%', 
      marginTop: theme.spacing(1),
    },
    submit: {
      margin: theme.spacing(3, 0, 2),
    },
  }));
const SignIn = () => {
    const classes = useStyles();
        const [msg, setMsg] = React.useState(null);
        const navigate = useNavigate();
        const email = React.useRef();
        const password = React.useRef();
        const handleLogin = e =>{
            e.preventDefault();
            AuthService.login(email.current.value,password.current.value)
                .then((response) => response.json())
                .then((responseData) => {
                    localStorage.setItem("user", JSON.stringify(responseData));
                    navigate("/index");
                    window.location.reload();
                  //  this.email.value = "";
                  //  this.password.value = "";
                })
                .catch(err=>{setMsg("Detail mismatch");})
        }

        
        
        return (
    
            <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
      <h1>{msg}</h1>
        <form  onSubmit={handleLogin} className={classes.form} noValidate style={{borderRadius: "20px",boxShadow: "5px 5px 5px black" , backgroundColor: "#FFDEAD", padding: "20px", margin: "30%" ,width:"600px"}}>
        <Avatar className={classes.avatar} style={{left: "45%"}}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign in
        </Typography>
          {/* <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="email"
            label="Email Address"
            name="email"
            autoComplete="email"
            autoFocus
            ref={email}
          /> */}
           
        <div className="form-group" style={{padding: "15px"}}>
        <input type="email" className="form-control" placeholder="Enter email" ref={email} required/>
        </div>

          {/* <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
            ref={password}
          /> */}
          
        <div className="form-group" style={{padding: "15px"}}>
           <input type="password" className="form-control" placeholder="Enter password" ref={password} required/>
        </div>

          {/* <FormControlLabel
            control={<Checkbox value="remember" color="primary" />}
            label="Remember me"
          /> */}
          <br></br>
              <div className="form-group" style={{padding: "15px"}}><button type="submit" className="btn btn-primary btn-block">Submit</button></div>
                <br></br>
          {/* <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
          >
            Sign In
          </Button> */}
        
          <Grid container>
            {/* <Grid item xs>
              <Link href="#" variant="body2">
                Forgot password?
              </Link>
            </Grid> */}
            <Grid item>
              <Link href="/sign-up" variant="body2">
                {"Don't have an account? Sign Up"}
              </Link>
            </Grid>
          </Grid>
        </form>
      </div>
      
    </Container>

            // <form method="post" id="login-form" onSubmit={handleLogin}>
         
               
            //     <h2><center>Sign In</center></h2>
            //     <div className="form-group">
            //         <label>Email address</label>
            //         <input type="email" className="form-control" placeholder="Enter email" ref={email} required/>
            //     </div>

            //     <div className="form-group">
            //         <label>Password</label>
            //         <input type="password" className="form-control" placeholder="Enter password" ref={password} required/>
            //     </div>
                
            //     <br></br>
            //     <div className="form-group"><button type="submit" className="btn btn-primary btn-block">Submit</button></div>
            //     <br></br>
                
            //     <h1>{msg}</h1>
            // </form>
            


        
         
        );
    }
export default SignIn;