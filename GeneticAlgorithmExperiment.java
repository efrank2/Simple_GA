/*
 *Eric Frank <Tuesday, June 12th 2012>
 *GeneticAlgorithmExperiment.java
 *Finds a series of algebraic expressions that produce a desired number. Uses
 *a genetic algorithm.
*/
import java.util.Random;
public class GeneticAlgorithmExperiment
{
	static double input = 0.0;
	static Random generator = new Random();
	public static void main(String[] args)
	{
		input = Double.parseDouble(args[0]);

		Chromosome winnerOfLife = execute();
		System.out.println(winnerOfLife.geneDecoder());
	}
//Encode chars as bitstrings.
	public static Chromosome[] firstGeneration()
	{
		Chromosome[] genome = new Chromosome[20];
		for(int i=0;i<20;i++)
			genome[i] = new Chromosome(StringGenerator());
		return genome;
	}
//Genrates a random 20 bit binary string. Used to create initial chromosomes.
	public static String StringGenerator()
	{
		String bits = "";
		Random r = new Random();
		for(int i=0; i<20; i++)
		{
			int x = 0;
			if(r.nextBoolean()) 
				x=1;
			bits += x;
		}
		return bits;
	}
	public static double fitnessTester(Chromosome test)
	{
		double value = test.geneDecoder();
		if(input != value)
			return 10*(1/(Math.abs(input-value)));
		else
			return 10000.0;
	}
//Crosses two chromosomes beginning at a random crosspoint.
	public static Chromosome ChromoCrosser(Chromosome one, Chromosome two)
	{
		String result = "";
		if(one.length() == two.length())
		{
			System.out.println("One: " + one.length() + " Two: " + two.length());
			int r = generator.nextInt(one.length());
			String tempOne = one.geneSequence.substring(0, r);
			String tempTwo = two.geneSequence.substring(r,two.length());
			result = tempOne + tempTwo;
		}
		else
		{
			System.out.println("Something's wrong. Terribly, terribly wrong. \n" + "One.length: " + one.length() + "\n" + "Two.length: " + two.length());
		}
		return new Chromosome(result);
	}
//0.1 percent of cycles, a random binary bit is flipped. This method flips the random bit.
	public static Chromosome Mutation(Chromosome input)
	{
		int r = generator.nextInt(input.length());
		String tempone = input.geneSequence.substring(0,r-1);
		String temptwo = input.geneSequence.substring(r+1,input.length());
		if((input.geneSequence.charAt(r)) == '1')
			return new Chromosome((tempone + "0" + temptwo));
		else
			return new Chromosome((tempone + "1" + temptwo));
	}
//Chooses two chromosomes using a Roulette Wheel algorithm.
	public static Chromosome[] ChromoCrossChooser(Chromosome[] genome)
	{
		double[][] fitnessValues = new double[2][genome.length];
		double[] pieChart = new double[genome.length+1];
		double blah=0.0;
//Find fitness values for all chromosomes, then choose the two to cross. Adds all the fitness values to blah, then checks random
//integer's position.
		for(int i=0; i<genome.length;i++)
		{
			fitnessValues[0][i] = fitnessTester(genome[i]);
			fitnessValues[1][i] = (double)i;
			blah+=fitnessValues[0][i];
			pieChart[i] = blah;
		}
		Chromosome[] Crosses = new Chromosome[2];
		for(int i=0;i<2;i++)
		{
			double r = blah*generator.nextDouble();
			int j=0;
			while(true)
			{
				if(pieChart[j]>r)
				{
					Crosses[i] = genome[j];
					break;
				}
				j++;
				
			}
		}
		return Crosses;
	}
	
	public static Chromosome execute()
	{
		int generation = 0;
		Chromosome[] oldGeneration = firstGeneration();
		Chromosome[] newGeneration = new Chromosome[20];
		int xyz = 0;
		double fitness = 0.0;
//Execution loop
		while(fitness<10.0)
		{
			generation++;
			System.out.println("Loop");
//Make new generation of 20 chromosomes.
			for(int i=0; i<20;i++)
			{	
				int Mutation = generator.nextInt(100);
				Chromosome[] Crosses = ChromoCrossChooser(oldGeneration);
		//		if(Mutation==1)
		//			newGeneration[i] = Mutation(ChromoCrosser(Crosses[0],Crosses[1]));
		//		else
					newGeneration[i] = ChromoCrosser(Crosses[0],Crosses[1]);
			}
			System.out.println("New Generation: " + generation);
			newGeneration = oldGeneration;
//Finds fittest of the generation. Used to end while loop.
			for(int i=0; i<20;i++)
			{
				double temp = fitnessTester(newGeneration[i]);
				if(temp>fitness)
				{
					fitness = temp;
					xyz = i;
					System.out.println("New fitness: " + fitness);
				}
			}
		}

		return newGeneration[xyz];
	}
}
