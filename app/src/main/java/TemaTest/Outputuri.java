package TemaTest;

public class Outputuri {
    Outputuri(){
    }

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

}
