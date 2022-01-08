package lottery;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
	private Scanner scan = new Scanner(System.in);
	private ArrayList<Integer[]> ticketCart = new ArrayList<>();

	public Main() {
		System.out.println("==== Welcome to Online Lottery System ====");
		System.out.println("\n1.Random Ticket \n2.Your Ticket \n3.Ticket Cart \n4.Edit Ticket \n5.Quit");
		menuSelection();
	}
	public void menuSelection() {
		System.out.println("Please select from the menu: ");
		int selection = scan.nextInt();
		switch(selection) {
			case 1:
				randomNumber();
				break;
			case 2:
				yourNumber();
				break;
			case 3:
				viewCart();
				menuSelection();
				break;
			case 4:
				 editOptions();
				break;
			case 5:
				initialise();
				break;
		}
	}
	public void randomNumber() {
		int limit = (int) (Math.random() * 5) + 4;
		Integer[] randomTicket  = new Integer[limit];
		for(int i = 0; i < limit; i++) {
			int numberRandom = (int) Math.floor(Math.random()*99+1);
			randomTicket[i] = numberRandom;
		}
		System.out.println("\nRandom ticket generated!");
		printNewTicket(randomTicket);
		askAddToCart(randomTicket);
	}
	public void yourNumber() {
		System.out.println("How many numbers do you like to have on your ticket:");
		int size = scan.nextInt();
		Integer[]yourTicket = null;
		if(size >= 4 && size <= 8) {
			yourTicket = new Integer[size];
			for(int i = 0; i < size; i++){
				System.out.println("Please input your choice of number:");
				int numberInput = scan.nextInt();
				if(numberInput>0 && numberInput <100) {
					yourTicket[i] = numberInput;
				}else {
					System.out.println("Invalid input. Number must be greater than 0 and less than 100.");
					yourNumber();
				}
			}
		}else {
			System.out.println("Invalid number. Lottery ticket must have numbers greater or equal than 4 and less or equal than 8.");
			yourNumber();
		}
		
		System.out.println("\nYour ticket generated!");
		printNewTicket(yourTicket);
		askAddToCart(yourTicket);
	}
	public void editOptions() {
		if(ticketCart != null) {
			viewCart();
			System.out.println("Please select a number to make change:");
			int op = scan.nextInt();
			if(op > 0 && op <= ticketCart.size()) {
				selectOption(op-1);
			}
		}else {
			System.out.println("Cart is empty! Do you want to add a ticket?");
			try {
				boolean add = scan.nextBoolean();
				if(add) {
					System.out.println();
				}else {
					menuSelection();
				}
			}catch(InputMismatchException exp){
				System.err.println("Please enter only true or false.");
			}
		}
	}
	public void selectOption(int index) {
		Integer[] currentTicket = ticketCart.get(index);
		System.out.println("1.remove a number\n2.insert a number\n3.replace a number");
		System.out.println("Please input your choice of number:");
		int option = scan.nextInt();
		if(option >= 1 && option <= 3) {
			switch(option) {
				case 1:
					removeNumber(currentTicket);
				case 2:
					insertNumber(currentTicket);
				case 3:
					replaceNumber(currentTicket);
			}
		}else {
			System.out.println("Invalid input. Must select a number from menu:");
			selectOption(index);
		}
	}
	public void removeNumber(Integer[] currentTicket) {
		if(currentTicket.length > 4) {
			System.out.println("Which number would you like to remove? Enter the position: ");
			int n = scan.nextInt();
			if(n > 0 && n <= currentTicket.length) {
				Integer[] newTicket = new Integer[currentTicket.length - 1];
				currentTicket = removedTicket(currentTicket, newTicket, (n - 1));
				System.out.println("Number has removed successfully");
			}else {
				System.out.println("Invalid selection. Number entered must be within the size range. ");
			}
		} else {
			System.out.println("Invalid removing. Ticket has minimum four numbers.");
		}
		menuSelection();
	}
	public Integer[] removedTicket(Integer[] currentTicket, Integer[] removed, int skipPosition) {
		int c = 0;
		for(int i = 0; i < currentTicket.length; i++) {
			if(i != skipPosition) {
				removed[c++] = currentTicket[i];
			}
		}
		return removed;
	}
	public void insertNumber(Integer[] currentTicket) {
		if(currentTicket.length < 8) {
			System.out.println("Enter the position you want to insert upwards (from 1 to size of all numbers): ");
			int n = scan.nextInt();
			System.out.println("Enter the number you want to insert (0-99): ");
			int number = scan.nextInt();
			if(n > 0 && n <= currentTicket.length && number > 0 && number < 100) {
				Integer[] newTicket = new Integer[currentTicket.length + 1];
				currentTicket = insertedTicket(currentTicket, newTicket, n, number);
				System.out.println("Number has removed successfully");
			}else {
				System.out.println("Invalid input. Number entered must be within range. ");
			}
		}else {
			System.out.println("Ticket already has 8 numbers. Could not insert more.");
		}
		menuSelection();
	}
	public Integer[] insertedTicket(Integer[] currentTicket, Integer[] inserted, int insertPosition, int insertNumber) {
		int i = 0;
		for(i = 0; i < insertPosition - 1; i++) {
			inserted[i] = currentTicket[i];					
		}
		inserted[insertPosition-1] = insertNumber;
		for(i = insertPosition; i < currentTicket.length + 1; i++) {
			inserted[i] = currentTicket[i - 1];
		}
		
		return inserted;
	}
	public void replaceNumber(Integer[] currentTicket) {
		System.out.println("Enter the number you want to replace: ");
		int num = scan.nextInt();
		for(int i = 0; i < currentTicket.length; i++) {
			if(num==currentTicket[i]) {
				currentTicket = replacedTicket(currentTicket, i+1, num);
			}else {
				System.out.println("Invalid input. Number must be from the ticket.");
			}
		}
		menuSelection();
	}
	public Integer[] replacedTicket(Integer[] currentTicket,int replacedPosition, int replacedWith) {	
		for(int i = 0; i < currentTicket.length; i++) {
			if(i == replacedPosition-1) {
				currentTicket[i] = currentTicket[i];
			}
			currentTicket[replacedPosition] = replacedWith;
		}
		return currentTicket;
	}
	public void printNewTicket(Integer[]ticket) {
		for(int i = 0; i < ticket.length; i++) {
			System.out.print(ticket[i]+" ");
		}
		System.out.println();
	}
	public void askAddToCart(Integer[]ticket) {
		try {
			System.out.println("Save current ticket? Please note that maximum number of tickets in cart is 5):");
			boolean b = scan.nextBoolean();
			if(b) {
				if(ticketCart.size() < 6) {
					ticketCart.add(ticket);
					System.out.println("Ticket is added to cart.");
				} else {
					System.out.println("You reached the limit! Cart could have no more than 5 tickets.");
				}
			} else {
				System.out.println("Ticket is not added.");
			}
			menuSelection();
		}catch(InputMismatchException exp){
			System.err.println("Please enter only true or false.");
		}
	}
	public void viewCart(){
		int counter = 1;
		for(Integer[]t:ticketCart) {
			System.out.print("\n"+(counter++)+": ");
			for(int i = 0; i < t.length; i++) {
				System.out.print(t[i]+" ");
			}
		}
		System.out.println();
	}
	public void initialise() {
		System.out.println('\f');
	}
	public static void main(String[] args) {
		new Main();
	}

}
