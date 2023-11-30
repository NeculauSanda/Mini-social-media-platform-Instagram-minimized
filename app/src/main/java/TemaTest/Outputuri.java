package TemaTest;

public class Outputuri {
    Outputuri(){
    }

    // region 1
    public void answerUser(int val) {
        if(val == 2) {
            System.out.println("{'status':'ok','message':'User created successfully'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'User already exists'}");
        } else if(val == 3) {
            System.out.println("{'status':'error','message':'Please provide password'}");
        } else if(val == 4) {
            System.out.println("{'status':'error','message':'Please provide username'}");
        }
    }

    // region 2
    public void answerPost(int val) {
        if(val == 2) {
            System.out.println("{'status':'error','message':'Login failed'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        } else if(val == 3) {
            System.out.println("{'status':'ok','message':'Post added successfully'}");
        } else if(val == 4) {
            System.out.println("{'status':'error','message':'No text provided'}");
        } else if(val == 5) {
            System.out.println("{'status':'error','message':'Post text length exceeded'}");
        }
    }

    // Region 3
    public void answerDeletePost(int val) {
        if(val == 2) {
            System.out.println("{'status':'error','message':'Login failed'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        } else if(val == 3) {
            System.out.println("{'status':'ok','message':'Post deleted successfully'}");
        } else if(val == 4) {
            System.out.println("{'status':'error','message':'No identifier was provided'}");
        } else if(val == 5) {
            System.out.println("{'status':'error','message':'The identifier was not valid'}");
        }
    }

    // Region 4
    public void answerFollow(int val) {
        if(val == 2) {
            System.out.println("{'status':'error','message':'Login failed'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        } else if(val == 3) {
            System.out.println("{'status':'ok','message':'Operation executed successfully'}");
        } else if(val == 4) {
            System.out.println("{'status':'error','message':'No username to follow was provided'}");
        } else if(val == 5) {
            System.out.println("{'status':'error','message':'The username to follow was not valid'}");
        }
    }

    // Region 5
    public void answerUnfollow(int val) {
        if(val == 2) {
            System.out.println("{'status':'error','message':'Login failed'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        } else if(val == 3) {
            System.out.println("{'status':'ok','message':'Operation executed successfully'}");
        } else if(val == 4) {
            System.out.println("{'status':'error','message':'No username to unfollow was provided'}");
        } else if(val == 5) {
            System.out.println("{'status':'error','message':'The username to unfollow was not valid'}");
        }
    }

    // Region 6
    public void answerLike(int val) {
        if(val == 2) {
            System.out.println("{'status':'error','message':'Login failed'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        } else if(val == 3) {
            System.out.println("{'status':'ok','message':'Operation executed successfully'}");
        } else if(val == 4) {
            System.out.println("{'status':'error','message':'No post identifier to like was provided'}");
        } else if(val == 5) {
            System.out.println("{'status':'error','message':'The post identifier to like was not valid'}");
        }
    }

    //Region 7
    public void answerUnlike(int val) {
        if(val == 2) {
            System.out.println("{'status':'error','message':'Login failed'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        } else if(val == 3) {
            System.out.println("{'status':'ok','message':'Operation executed successfully'}");
        } else if(val == 4) {
            System.out.println("{'status':'error','message':'No post identifier to unlike was provided'}");
        } else if(val == 5) {
            System.out.println("{'status':'error','message':'The post identifier to unlike was not valid'}");
        }
    }

    //Region 13
    public void answerComPost(int val) {
        if(val == 2) {
            System.out.println("{'status':'error','message':'Login failed'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        } else if(val == 3) {
            System.out.println("{'status':'ok','message':'Comment added successfully'}");
        } else if(val == 4) {
            System.out.println("{'status':'error','message':'No text provided'}");
        } else if(val == 5) {
            System.out.println("{'status':'error','message':'Comment text length exceeded'}");
        }
    }

    //Region 14
    public void answerDeleteComPost(int val) {
        if(val == 2) {
            System.out.println("{'status':'error','message':'Login failed'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        } else if(val == 3) {
            System.out.println("{'status':'ok','message':'Operation executed successfully'}");
        } else if(val == 4) {
            System.out.println("{'status':'error','message':'No identifier was provided'}");
        } else if(val == 5) {
            System.out.println("{'status':'error','message':'The identifier was not valid'}");
        }
    }

    //Region 8
    public void answerLikeComPost(int val) {
        if(val == 2) {
            System.out.println("{'status':'error','message':'Login failed'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        } else if(val == 3) {
            System.out.println("{'status':'ok','message':'Operation executed successfully'}");
        } else if(val == 4) {
            System.out.println("{'status':'error','message':'No comment identifier to like was provided'}");
        } else if(val == 5) {
            System.out.println("{'status':'error','message':'The comment identifier to like was not valid'}");
        }
    }

    //Region 9
    public void answerUnlikeComPost(int val) {
        if(val == 2) {
            System.out.println("{'status':'error','message':'Login failed'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        } else if(val == 3) {
            System.out.println("{'status':'ok','message':'Operation executed successfully'}");
        } else if(val == 4) {
            System.out.println("{'status':'error','message':'No comment identifier to unlike was provided'}");
        } else if(val == 5) {
            System.out.println("{'status':'error','message':'The comment identifier to unlike was not valid'}");
        }
    }

    //Region 10
    public void answerfolowing(int val) {
        if(val == 2) {
            System.out.println("{'status':'error','message':'Login failed'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        }
    }

    //Region 11
    public void answerfolowingpost(int val) {
        if(val == 2) {
            System.out.println("{'status':'error','message':'Login failed'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        } else if(val == 4) {
            System.out.println("{ 'status' : 'error', 'message' : 'No username to list posts was provided'}");
        } else if(val == 5) {
            System.out.println("{ 'status' : 'error', 'message' : 'The username to list posts was not valid'}");
        }
    }

    //Region 12
    public void answerpostdetails(int val) {
        if(val == 2) {
            System.out.println("{'status':'error','message':'Login failed'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        } else if(val == 4) {
            System.out.println("{ 'status' : 'error', 'message' : 'No post identifier was provided'}");
        }
    }

    // Region 16-20
    public void answeregion16(int val) {
        if(val == 2) {
            System.out.println("{'status':'error','message':'Login failed'}");
        } else if(val == 1) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        } else if(val == 4) {
            System.out.println("{ 'status' : 'error', 'message' : 'No username to list followers was provided'}");
        } else if(val == 5) {
            System.out.println("{ 'status' : 'error', 'message' : 'The username to list followers was not valid'}");
        }
    }
}
