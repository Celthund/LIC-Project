-- TestBench Template 

  LIBRARY ieee;
  USE ieee.std_logic_1164.ALL;
  USE ieee.numeric_std.ALL;

  ENTITY KD_TEST IS
  END KD_TEST;

  ARCHITECTURE behavior OF KD_TEST IS 
	-- Component Declaration
          COMPONENT KeyDecoder
          PORT( 
					RST: in STD_LOGIC;
					CLK : in  STD_LOGIC;
					KAck : in  STD_LOGIC;
					KVal: out STD_LOGIC;
					K : out  STD_LOGIC_VECTOR (3 downto 0);
					PENC_IN: in  STD_LOGIC_VECTOR (3 downto 0);
					DEC_OUT : out STD_LOGIC_VECTOR (2 downto 0)
          );
          END COMPONENT;
				
			 -- Inputs
          SIGNAL CLK :  std_logic := '0';
			 SIGNAL RST :  std_logic := '0';
          SIGNAL KAck :  std_logic := '0';
			 SIGNAL PENC_IN :  std_logic_vector(3 downto 0);
          
			 -- Outputs
			 SIGNAL KVal :  std_logic := '0';
			 SIGNAL DEC_OUT : STD_LOGIC_VECTOR (2 downto 0);
			 SIGNAL K : STD_LOGIC_VECTOR (3 downto 0);
			 
			 -- Clock period definitions
			 constant CLK_period : time := 10 ns;
  BEGIN

  -- Component Instantiation
          KD: KeyDecoder PORT MAP(
					RST => RST,
					CLK => CLK,
					KAck =>KAck,
					KVal=> KVal,
					K => K ,
					PENC_IN => PENC_IN,
					DEC_OUT => DEC_OUT
          );

		CLK_process :process
	   begin
			CLK <= '0';
			wait for CLK_period/2;
			CLK <= '1';
			wait for CLK_period/2;
		end process;

  --  Test Bench Statements
     tb : PROCESS
     BEGIN

			RST <= '1';
			PENC_IN <= "1111";
			KAck <= '0';
         wait for 100 ns; -- wait until global set/reset completes
			RST <= '0';
        -- Add user defined stimulus here
			
			-- Test for key presses in first column
			PENC_IN <= "1110";
			wait for CLK_period * 10; -- K <= "0000"; Kval <= '1'
			
			KAck <= '1';
			wait for CLK_period * 10; -- Reset; Kval <= '0'
			
			KAck <= '0';
			
			PENC_IN <= "1111";
			wait for CLK_period * 10;

			
			
			PENC_IN <= "1101";
			wait for CLK_period * 10; -- K <= "0000"; Kval <= '1'
			
			KAck <= '1';
			wait for CLK_period * 10; -- Reset; Kval <= '0'
			
			KAck <= '0';
			
			PENC_IN <= "1111";
			wait for CLK_period * 10;
			
			
			wait; -- will wait forever
     END PROCESS tb;
  --  End Test Bench 

  END;
