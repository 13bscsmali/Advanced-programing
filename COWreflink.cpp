#include<iostream>
#include<memory>

class COW_ptr2 {
public:
	char* strin;
	int ref_count;
	int length;
	COW_ptr2* next;
	COW_ptr2* prev;

	COW_ptr2(){
	this->strin = 0;
    this->length = 0;
    this->ref_count = 1;
	this->next = NULL;
	this->prev = NULL;
	};

	COW_ptr2(COW_ptr2& new_string){
	this->strin = new char[new_string.length];
    this->length = new_string.length;
	this->smartCopy(new_string.strin, length);
	
	if(this->next == NULL && this->prev == NULL)
	{
	this->next = new_string.next;
	this->prev = new_string.prev;
	}
	else
	{
		COW_ptr2* cc;
		cc= this;
		while(cc->next !=NULL)
		{
			cc= cc->next;
		}
		cc->next = new_string.next;

	}

	};
	COW_ptr2(char* newString, int length1)
	{
	length = length1;
    delete[] strin;
    strin = new char[length1];
    strin = newString;
	
	};
	~COW_ptr2(){
		this->ref_count--;
		if((this->prev==NULL) && (this->next==NULL) )
		{
			delete[] this;
		}
		else
		{
			COW_ptr2* p = this->next;
			 this->next = this->next->next;
			 this->prev = this->next->prev;
			 this->strin = p->strin;
			 this->length = p->length;
			 this->ref_count = p->ref_count;
			 delete[] p;

		}
	};

	void append(char c) {
    this->strin[this->length] = c;
	this->length++;
	};


	//get character at index if it is less than length

	char charAt(int index) const {
    if (index < length) {
        return strin[index];
    } else {
        //throw new IndexOutOfBoundException();

	}
	};

	void equalsto(COW_ptr2& new_string){
		this->prev = &new_string;
		COW_ptr2* PP = this;

		this->prev = NULL;
		this->strin = new char[new_string.length];
		this->length = new_string.length;
		COW_ptr2(new_string.strin,new_string.length);
		this->next = PP; 

		//new_string.~COW_ptr2();
		//this->ref_count = this->ref_count+new_string.ref_count; 
	};

	void smartCopy(char* new_string, int len) {
    
    this->length = len;
    int it = 0;
    while (it < len) {
        *strin++ = *new_string++;
        it++;
    }
};
//make a smart copy of this string into the passed string

void revSmartCopy(char* newString) {
    int it = 0;
    while (it < length) {
        newString[it] = strin[it];
        it++;
    }
}

void reserve(int n) {
    if (length < n) {
        int newlength = n; //max(_length*2,n);
        char* newbuf = new char[newlength];
        //copy contents of the stored string in the new buffer
        this->revSmartCopy(newbuf);

        //return stuff from the new buffer to the stored buffer
        delete[] this->strin;
        this->strin = newbuf;
        this->length = newlength;
    }
}

};
