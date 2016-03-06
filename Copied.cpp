#include<iostream>
#include<memory>

class Copy_ptr {
public:
	char* strin;
	int ref_count;
	int length;

	Copy_ptr(){
	this->strin = 0;
    this->length = 0;
    this->ref_count = 1;
	};
	Copy_ptr(Copy_ptr& new_string){
	this->strin = new char[new_string.length];
    this->length = new_string.length;
	this->smartCopy(new_string.strin, length);
	};
	Copy_ptr(char* newString, int length1)
	{
	length = length1;
    delete[] strin;
    strin = new char[length1];
    strin = newString;

	};
	~Copy_ptr(){
		delete[] this;
	};

	void append(char c) {
    this->strin[this->length] = c;
	};


	//get character at index if it is less than length

	char charAt(int index) const {
    if (index < length) {
        return strin[index];
    } else {
        //throw new IndexOutOfBoundException();

	}
	};

	void equalsto(Copy_ptr& new_string){

		this->strin = new char[new_string.length];
		this->length = new_string.length;
		Copy_ptr(new_string.strin,new_string.length);
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
